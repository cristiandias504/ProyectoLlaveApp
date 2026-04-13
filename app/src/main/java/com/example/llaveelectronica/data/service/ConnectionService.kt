package com.example.llaveelectronica.data.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothProfile
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.llaveelectronica.ProcesadorDatos
import java.util.UUID

@SuppressLint("MissingPermission")
class ConnectionService: Service() {
    companion object {
        private const val ESP32_MAC = "30:C6:F7:22:5C:F6"
        private val SERVICE_UUID =
            UUID.fromString("12345678-1234-1234-1234-1234567890ab")
        private val RX_UUID =
            UUID.fromString("12345678-1234-1234-1234-1234567890ac")
        private val TX_UUID =
            UUID.fromString("12345678-1234-1234-1234-1234567890ad")
        private val CCCD_UUID =
            UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")
        private const val TAG = "ServicioConexion"
    }

    private var bluetoothGatt: BluetoothGatt? = null
    private var rxCharacteristic: BluetoothGattCharacteristic? = null
    private var txCharacteristic: BluetoothGattCharacteristic? = null
    private val bluetoothAdapter: BluetoothAdapter? =
        BluetoothAdapter.getDefaultAdapter()
    private var escribiendo = false
    private var conexionEstablecida = false
    private var servicioFinalizado = false
    private var dispositivoGuardado: BluetoothDevice? = null
    private val procesadorDatos = ProcesadorDatos()


    // ==== Receptor de broadcast ====
    private val receptorMensaje = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val mensaje = intent?.getStringExtra("Mensaje") ?: "Sin mensaje"
            //Log.d(TAG, "Desde MainActivity: $mensaje")

            when (mensaje) {
                "Verificación de estado" -> enviarBroadcast("Respuesta Verificación de estado = $conexionEstablecida")
                "Enviar 301" -> enviarMensajeBLE("301")
                "Enviar 302" -> enviarMensajeBLE("302")
            }
        }
    }

    // ==== Enviar Mensaje por broadcast ====
    private fun enviarBroadcast(mensaje: String) {
        val enviarBroadcast = Intent("com.example.pruebaconexion.BroadCast").apply {
            setPackage(packageName)
            putExtra("Mensaje", mensaje)
        }
        Log.d(TAG, mensaje)
        sendBroadcast(enviarBroadcast)
    }

    override fun onCreate() {
        super.onCreate()

        crearCanal()
        iniciarForeground()
        conectarDispositivoFijo()

        val filter = IntentFilter("com.example.pruebaconexion.BroadCast")
        ContextCompat.registerReceiver(
            this, receptorMensaje, filter, ContextCompat.RECEIVER_NOT_EXPORTED
        )
        enviarBroadcast("Servicio iniciado correctamente")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        servicioFinalizado = true
        bluetoothGatt?.let {
            refreshGattCache(it)
            it.disconnect()
            it.close()
        }
        bluetoothGatt = null
        unregisterReceiver(receptorMensaje)

        enviarBroadcast("Conexión Bluetooth finalizada")
        Log.d("ServicioConexion", "🛑 Finalizando Servicio")
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    // ==== Verificación de permisos ====
    private fun permisos(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.BLUETOOTH_CONNECT
        ) == PackageManager.PERMISSION_GRANTED
    }


    // ===== Notificación =====
    private fun crearCanal() {
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                "canal_ble",
                "Servicio BLE",
                NotificationManager.IMPORTANCE_LOW
            )
            /*Context.*/getSystemService(NotificationManager::class.java)
                .createNotificationChannel(canal)
        //}
    }
    private fun iniciarForeground() {
        val notification = NotificationCompat.Builder(this, "canal_ble")
            .setContentTitle("BLE activo")
            .setContentText("Esperando ESP32")
            .setSmallIcon(android.R.drawable.stat_sys_data_bluetooth)
            .build()

        startForeground(1, notification)
    }
    private fun conectarDispositivoFijo() {
        if (bluetoothAdapter == null) {
            Log.e(TAG, "❌ Bluetooth no disponible")
            return
        }
        if (!permisos()){
            Log.e(TAG, "❌ Sin permiso BLUETOOTH_CONNECT")
            return
        }

        val device = bluetoothAdapter.getRemoteDevice(ESP32_MAC)
        dispositivoGuardado = device

        Log.d(TAG, "🔗 Conectando a ESP32: ${device.address}")
        conectarGatt(device, autoConnect = false)
    }


    // ===== Conexión GATT =====
    private fun conectarGatt(device: BluetoothDevice, autoConnect: Boolean) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) return
        bluetoothGatt = device.connectGatt(this, autoConnect, gattCallback)
    }
    //@SuppressLint("MissingPermission")
    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(
            gatt: BluetoothGatt,
            status: Int,
            newState: Int
        ) {
            Log.d(TAG, "🔄 ConnectionState status=$status newState=$newState")
            if (status != BluetoothGatt.GATT_SUCCESS) {
                Log.e(TAG, "❌ Error GATT: $status")
                gatt.close()
                reiniciarConexion()
                return
            }
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.d(TAG, "🔐 Conectado, descubriendo servicios")
                gatt.discoverServices()
                enviarBroadcast("Conexion establecida Correctamente")
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.w(TAG, "⚠️ Desconectado")
                reiniciarConexion()
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            val service = gatt.getService(SERVICE_UUID) ?: return

            rxCharacteristic = service.getCharacteristic(RX_UUID)
            txCharacteristic = service.getCharacteristic(TX_UUID)

            activarNotificaciones(gatt)
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic
        ) {
            val mensajeRecibido = characteristic.value.toString(Charsets.UTF_8)
            Log.d(TAG, "📥 RX: $mensajeRecibido")
            if (mensajeRecibido.length == 15) {
                val respuesta = procesadorDatos.procesarClaveInical(mensajeRecibido)
                if (respuesta == "200"){
                    enviarMensajeBLE("200")
                }
            } else if (mensajeRecibido.length == 5) {
                val respuesta = procesadorDatos.procesarClaveDinamica(mensajeRecibido)
                enviarMensajeBLE(respuesta)
                Log.d(TAG, "Clave Descifrada: $respuesta")
            } else if (mensajeRecibido.length == 4) {
                if (mensajeRecibido == "301Y"){
                    enviarBroadcast("301Y")
                } else if (mensajeRecibido == "302Y") {
                    enviarBroadcast("302Y")
                }
            }
        }

        override fun onCharacteristicWrite(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            escribiendo = false
            Log.d(TAG, "✍️ Escritura completada status=$status")
        }
    }

    // ===== Envío =====
    private fun enviarMensajeBLE(mensaje: String) {
        if (!conexionEstablecida || escribiendo) {
            Log.w(TAG, "⚠️ No Se encuentra listo para envíar")
            return
        }

        rxCharacteristic?.value = (mensaje).toByteArray()
        escribiendo = true
        bluetoothGatt?.writeCharacteristic(rxCharacteristic)
        Log.d(TAG, "📤 TX: $mensaje")
    }


    // ===== Notificaciones =====
    private fun activarNotificaciones(gatt: BluetoothGatt) {
        Log.d(TAG, "🔔 Activando notificaciones")
        gatt.setCharacteristicNotification(txCharacteristic, true)

        val descriptor = txCharacteristic?.getDescriptor(CCCD_UUID)
        if (descriptor == null) {
            Log.e(TAG, "❌ CCCD no encontrado")
            return
        }

        descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
        val ok = gatt.writeDescriptor(descriptor)

        Log.d(TAG, "✍️ writeDescriptor enviado: $ok")
        conexionEstablecida = true
    }


    private fun refreshGattCache(gatt: BluetoothGatt): Boolean {
        return try {
            val refresh = gatt.javaClass.getMethod("refresh")
            refresh.invoke(gatt) as Boolean
        } catch (e: Exception) {
            Log.e(TAG, "❌ No se pudo refrescar GATT", e)
            false
        }
    }

    // ===== Reconexión =====
    private fun reiniciarConexion() {
        Log.w(TAG, "🔄 Reiniciando conexión")
        conexionEstablecida = false

        bluetoothGatt?.let {
            refreshGattCache(it)
            it.disconnect()
            it.close()
        }
        bluetoothGatt = null

        enviarBroadcast("Desconectado, Reiniciando conexion")

        Handler(Looper.getMainLooper()).postDelayed({
            if (!servicioFinalizado && dispositivoGuardado != null) {
                Log.d(TAG, "🔁 Esperando reconexión automática")
                conectarGatt(dispositivoGuardado!!, autoConnect = true)
            }
        }, 3000)
    }
}
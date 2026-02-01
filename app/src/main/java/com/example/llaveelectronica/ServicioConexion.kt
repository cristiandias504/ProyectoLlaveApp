package com.example.llaveelectronica

import android.Manifest
import android.app.*
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
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import java.util.UUID

class ServicioConexion: Service() {
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
    private var dispositivoGuardado: BluetoothDevice? = null

    // Receptor de broadcast
    private val receptorMensaje = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val mensaje = intent?.getStringExtra("Mensaje") ?: "Sin mensaje"
            Log.d(TAG, "Desde MainActivity: $mensaje")

            when (mensaje) {

            }
        }
    }

    // Enviar Mensaje por broadcast
    private fun enviarBroadcast(mensaje: String) {
        val enviarBroadcast = Intent("com.example.pruebaconexion.MensajeDeServicio").apply {
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

        val filter = IntentFilter("com.example.pruebaconexion.MensajeDeActivity")
        ContextCompat.registerReceiver(
            this, receptorMensaje, filter, ContextCompat.RECEIVER_NOT_EXPORTED
        )

        enviarBroadcast("Servicio iniciado correctamente")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onDestroy() {
        enviarBroadcast("Servicio Finalizado")

        unregisterReceiver(receptorMensaje)

        super.onDestroy()
    }

    // ===== NOTIFICACIÓN =====
    private fun crearCanal() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                "canal_ble",
                "Servicio BLE",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java)
                .createNotificationChannel(canal)
        }
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
            Log.e(com.example.llaveelectronica.ServicioConexion.Companion.TAG, "❌ Bluetooth no disponible")
            return
        }
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e(com.example.llaveelectronica.ServicioConexion.Companion.TAG, "❌ Sin permiso BLUETOOTH_CONNECT")
            return
        }

        val device = bluetoothAdapter.getRemoteDevice(com.example.llaveelectronica.ServicioConexion.Companion.ESP32_MAC)
        dispositivoGuardado = device

        Log.d(com.example.llaveelectronica.ServicioConexion.Companion.TAG, "🔗 Conectando a ESP32: ${device.address}")
        conectarGatt(device, autoConnect = false)
    }

    // ===== CONEXIÓN GATT =====
    private fun conectarGatt(device: BluetoothDevice, autoConnect: Boolean) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) return
        bluetoothGatt = device.connectGatt(this, autoConnect, gattCallback)
    }

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(
            gatt: BluetoothGatt,
            status: Int,
            newState: Int
        ) {
            Log.d(com.example.llaveelectronica.ServicioConexion.Companion.TAG, "🔄 ConnectionState status=$status newState=$newState")
            if (status != BluetoothGatt.GATT_SUCCESS) {
                Log.e(com.example.llaveelectronica.ServicioConexion.Companion.TAG, "❌ Error GATT: $status")
                gatt.close()
                return
            }
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.d(com.example.llaveelectronica.ServicioConexion.Companion.TAG, "🔐 Conectado, descubriendo servicios")
                gatt.discoverServices()
                enviarBroadcast("Conexion establecida Correctamente")
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.w(com.example.llaveelectronica.ServicioConexion.Companion.TAG, "⚠️ Desconectado")
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            val service = gatt.getService(com.example.llaveelectronica.ServicioConexion.Companion.SERVICE_UUID) ?: return

            rxCharacteristic = service.getCharacteristic(com.example.llaveelectronica.ServicioConexion.Companion.RX_UUID)
            txCharacteristic = service.getCharacteristic(com.example.llaveelectronica.ServicioConexion.Companion.TX_UUID)

            activarNotificaciones(gatt)
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic
        ) {
            val mensajeRecibido = characteristic.value.toString(Charsets.UTF_8)
            Log.d(com.example.llaveelectronica.ServicioConexion.Companion.TAG, "📥 RX: $mensajeRecibido")
        }

        override fun onCharacteristicWrite(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            Log.d(TAG, "✍️ Escritura completada status=$status")
        }

        // ===== NOTIFICACIONES =====
        private fun activarNotificaciones(gatt: BluetoothGatt) {
            Log.d(com.example.llaveelectronica.ServicioConexion.Companion.TAG, "🔔 Activando notificaciones")
            gatt.setCharacteristicNotification(txCharacteristic, true)

            val descriptor = txCharacteristic?.getDescriptor(com.example.llaveelectronica.ServicioConexion.Companion.CCCD_UUID)
            if (descriptor == null) {
                Log.e(com.example.llaveelectronica.ServicioConexion.Companion.TAG, "❌ CCCD no encontrado")
                return
            }

            descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
            val ok = gatt.writeDescriptor(descriptor)

            Log.d(com.example.llaveelectronica.ServicioConexion.Companion.TAG, "✍️ writeDescriptor enviado: $ok")
        }
    }
}
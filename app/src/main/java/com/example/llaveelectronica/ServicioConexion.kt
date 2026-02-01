package com.example.llaveelectronica

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class ServicioConexion: Service() {

    companion object {
        private const val TAG = "ServicioConexion"
    }

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
}
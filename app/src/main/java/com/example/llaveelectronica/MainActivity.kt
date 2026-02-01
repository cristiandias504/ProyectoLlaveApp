package com.example.llaveelectronica

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.llaveelectronica.ui.theme.LlaveElectronicaTheme

class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    // ID de la solicitud de permiso
    private val requestPermissionsCode = 1001

    // Receptor de Broadcast
    private val receptorMensaje = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val mensaje = intent?.getStringExtra("Mensaje") ?: "Sin mensaje"
            Log.d(TAG, "Desde ServicioConexion: $mensaje")

            when (mensaje) {
                "Servicio iniciado correctamente" -> {
                    Log.d(TAG, mensaje)
                    btnConectar.setText("Desconectar")
                }
                "Servicio Finalizado" -> {
                    Log.d(TAG, mensaje)
                    btnConectar.setText("Conectar")
                }
            }
        }
    }

    // Enviar Mensaje por broadcast
    private fun enviarBroadcast(mensaje: String) {
        val enviarBroadcast = Intent("com.example.pruebaconexion.MensajeDeActivity").apply {
            setPackage(packageName)
            putExtra("Mensaje", mensaje)
        }

        Log.d(TAG, mensaje)
        sendBroadcast(enviarBroadcast)
    }


    private lateinit var btnConectar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.main_activity)

        solicitarPermisos()

        btnConectar = findViewById(R.id.Conectar)

        btnConectar.setOnClickListener {
            val intent = Intent(this, ServicioConexion::class.java)
            if (btnConectar.text.toString() == "Conectar"){
                startService(intent)
                Log.d(TAG, "Boton presionado")
            } else if (btnConectar.text.toString() == "Desconectar"){
                stopService(intent)
                Log.d(TAG, "Boton presionado")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter("com.example.pruebaconexion.MensajeDeServicio")
        ContextCompat.registerReceiver(
            this, receptorMensaje, filter, ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receptorMensaje)
    }

    private fun solicitarPermisos() {
        val permisos = mutableListOf<String>()

        permisos += Manifest.permission.BLUETOOTH_CONNECT

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permisos += Manifest.permission.POST_NOTIFICATIONS
        }

        val faltantes = permisos.filter {
            ActivityCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (faltantes.isNotEmpty()) {
            requestPermissions(faltantes.toTypedArray(), requestPermissionsCode)
        }
    }
}
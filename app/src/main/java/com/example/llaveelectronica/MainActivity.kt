package com.example.llaveelectronica

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.Toast
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

    // ==== ID de la solicitud de permiso ====
    private val requestPermissionsCode = 1001

    // Variable estado de conexion
    private var estadoConexion = 0  // 0=Desconectado, 1=Conectado, 2=Conectando

    // ==== Receptor de Broadcast ====
    private val receptorMensaje = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val mensaje = intent?.getStringExtra("Mensaje") ?: "Sin mensaje"
            Log.d(TAG, "Desde ServicioConexion: $mensaje")

            when (mensaje) {
                "Servicio iniciado correctamente" -> btnConectar.setText("Conectando")
                "Desconectado, Reiniciando conexion" -> btnConectar.setText("Conectando")
                "Conexion establecida Correctamente" -> btnConectar.setText("Desconectar")
                "Conexión Bluetooth finalizada" -> btnConectar.setText("Conectar")
                "Respuesta Verificación de estado = true" -> btnConectar.setText("Desconectar")
                "Respuesta Verificación de estado = false" -> btnConectar.setText("Conectando")
            }
        }
    }

    // ==== Enviar Mensaje por broadcast ====
    private fun enviarBroadcast(mensaje: String) {
        val enviarBroadcast = Intent("com.example.pruebaconexion.MensajeDeActivity").apply {
            setPackage(packageName)
            putExtra("Mensaje", mensaje)
        }

        Log.d(TAG, mensaje)
        sendBroadcast(enviarBroadcast)
    }


    private lateinit var btnConectar: Button
    private lateinit var btnProximidad: Button
    private lateinit var btnApagar: Button
    private lateinit var btnAlarma: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.main_activity)

        solicitarPermisos()

        btnConectar = findViewById(R.id.Conectar)
        btnProximidad = findViewById(R.id.Proximidad)
        btnApagar = findViewById(R.id.Apagar)
        btnAlarma = findViewById(R.id.Alarma)

        btnConectar.setOnClickListener {
            if (!verificarPermisosBluetooth()) {
                Toast.makeText(this, "Permiso Bluetooth no otorgado", Toast.LENGTH_SHORT).show()
                solicitarPermisos()
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", packageName, null)
                )
                startActivity(intent)
                return@setOnClickListener
            }
            val intent = Intent(this, ServicioConexion::class.java)
            if (btnConectar.text.toString() == "Conectar"){
                startService(intent)
                Log.d(TAG, "Botón presionado")
            } else if (btnConectar.text.toString() == "Desconectar" || btnConectar.text.toString() == "Conectando"){
                stopService(intent)
                Log.d(TAG, "Botón presionado")
            }
        }

        btnProximidad.setOnClickListener {
            enviarBroadcast("Proximidad")
        }

        btnApagar.setOnClickListener {
            enviarBroadcast("Enviar 301")
        }

        btnAlarma.setOnClickListener {
            enviarBroadcast("Enviar 302")
        }
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter("com.example.pruebaconexion.MensajeDeServicio")
        ContextCompat.registerReceiver(
            this, receptorMensaje, filter, ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }
    override fun onResume() {
        super.onResume()
        // Enviar el broadcast para verificar el estado
        enviarBroadcast("Verificación de estado")
    }
    override fun onStop() {
        super.onStop()
        unregisterReceiver(receptorMensaje)
    }

    private fun verificarPermisosBluetooth(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.BLUETOOTH_CONNECT
        ) == PackageManager.PERMISSION_GRANTED
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == requestPermissionsCode) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Toast.makeText(this, "Permisos otorgados", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "La app necesita permisos para funcionar",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
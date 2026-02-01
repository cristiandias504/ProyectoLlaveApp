package com.example.llaveelectronica

import android.Manifest
import android.content.Intent
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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.llaveelectronica.ui.theme.LlaveElectronicaTheme

class MainActivity : ComponentActivity() {

    // ID de la solicitud de permiso
    private val requestPermissionsCode = 1001

    private lateinit var btnConectar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.main_activity)

        solicitarPermisos()

        btnConectar = findViewById(R.id.Conectar)

        btnConectar.setOnClickListener {
            if (btnConectar.text.toString() == "Conectar"){
                val intent = Intent(this, ServicioConexion::class.java)
                startService(intent)
                Log.d("MainActivity", "Boton presionado")
            }
        }
    }

    private fun solicitarPermisos() {
        val permisos = mutableListOf<String>()

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
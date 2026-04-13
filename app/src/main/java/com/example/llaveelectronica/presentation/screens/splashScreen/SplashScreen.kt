package com.example.llaveelectronica.presentation.screens.splashScreen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.llaveelectronica.presentation.screens.setupIntoScreen.SetupIntoViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "SplashScreen"

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SetupIntoViewModel
) {
    val state by viewModel.setupIntoState
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var serviceStatus by remember { mutableIntStateOf(0) }

    // Configuración del Receptor de Broadcast
    DisposableEffect(context) {
        val receptorMensaje = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val mensaje = intent?.getStringExtra("Mensaje") ?: "Sin mensaje"
                //Log.d(TAG, "Recibido desde ConnectionService: $mensaje")

                when (mensaje) {
                    "Respuesta Verificación de estado = false" -> serviceStatus = 1
                    "Respuesta Verificación de estado = true" -> serviceStatus = 2
                }
            }
        }

        val filter = IntentFilter("com.example.pruebaconexion.BroadCast")
        ContextCompat.registerReceiver(context, receptorMensaje, filter, ContextCompat.RECEIVER_NOT_EXPORTED)

        onDispose {
            context.unregisterReceiver(receptorMensaje)
        }
    }

    SplashScreenUI(
        onAnimationFinished = {
            scope.launch {
                enviarBroadcast(context)
                delay(100) // Tiempo para esperar respuesta del broadcast

                Log.d(TAG, "serviceStatus = $serviceStatus")

                val destination =
                    if (state.isSetupCompleted) {
                        if (serviceStatus == 1 || serviceStatus == 2) {
                            "mainScreenUI"
                        } else {
                            "authentication"
                        }
                    } else "welcome"

                navController.navigate(destination) {
                    popUpTo("splash") { inclusive = true }
                    launchSingleTop = true
                }
            }
        },
        isConfig = state.isSetupCompleted
    )
}

// Enviar Mensaje por broadcast
private fun enviarBroadcast(context: Context) {
    val enviarBroadcast = Intent("com.example.pruebaconexion.BroadCast").apply {
        setPackage(context.packageName)
        putExtra("Mensaje", "Verificación de estado")
    }
    context.sendBroadcast(enviarBroadcast)
    Log.d(TAG, "Enviando a Servicio: Verificación de estado")
}
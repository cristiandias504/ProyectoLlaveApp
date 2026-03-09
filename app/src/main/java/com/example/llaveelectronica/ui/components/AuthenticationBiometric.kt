package com.example.llaveelectronica.ui.components

import android.content.Context
import android.content.ContextWrapper
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.llaveelectronica.data.SetupRepository
import com.example.llaveelectronica.presentation.screens.setupIntoScreen.SetupIntoViewModel
import com.example.llaveelectronica.ui.theme.LlaveElectronicaTheme
import kotlinx.coroutines.delay

@Composable
fun AuthenticationBiometric(
    viewModel: SetupIntoViewModel
) {
    val authenticationBiometricViewModel by viewModel.setupIntoState

    val context = LocalContext.current
    val activity = remember(context) { context.findFragmentActivity() }
    val executor = ContextCompat.getMainExecutor(context)
    val biometricManager = remember { BiometricManager.from(context) }

    // Estado para controlar si hay biometría disponible
    var canAuthenticate by remember { mutableStateOf<Boolean?>(null) }

    // Configuración del prompt
    val promptInfo = remember {
        BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticación biométrica")
            //.setSubtitle("Confirma tu huella para activar la seguridad")
            .setNegativeButtonText("Cancelar")
            .build()
    }

    // Inicializar BiometricPrompt solo si hay Activity
    val biometricPrompt = remember(activity) {
        if (activity == null) return@remember null
        BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    viewModel.registerAuthenticationBiometric(true)
                    //viewModel.onNextClicked()
                }

                //override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                //    super.onAuthenticationError(errorCode, errString)
                //    Toast.makeText(context, "Error: $errString", Toast.LENGTH_SHORT).show()
                //}

                //override fun onAuthenticationFailed() {
                //    super.onAuthenticationFailed()
                //    Toast.makeText(context, "Autenticación fallida", Toast.LENGTH_SHORT).show()
                //}
            }
        )
    }

    // Verificar hardware y datos biométricos
    LaunchedEffect(Unit) {

        val result = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)

        when (result) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                canAuthenticate = true
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                canAuthenticate = false
                Toast.makeText(context, "No hay sensor biométrico en el dispositivo", Toast.LENGTH_LONG).show()
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                canAuthenticate = false
                Toast.makeText(context, "Hardware biométrico temporalmente no disponible", Toast.LENGTH_LONG).show()
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                canAuthenticate = false
                Toast.makeText(context, "No hay datos biométricos registrados. Configúralos en ajustes.", Toast.LENGTH_LONG).show()
            }
            else -> {
                canAuthenticate = false
                Toast.makeText(context, "Error desconocido de biometría", Toast.LENGTH_SHORT).show()
            }
        }
        if (canAuthenticate == false) viewModel.onNextClicked()
    }

    // UI siempre visible
    Box(
        modifier = Modifier
            //.fillMaxWidth()
            .height(350.dp)
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Seguridad Biométrica",
                color = MaterialTheme.colorScheme.surfaceDim,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            val visible = authenticationBiometricViewModel.isAuthenticationBiometricActive
            var startAnimation by remember { mutableStateOf(false) }

            if (!visible) {
                Text(
                    text = "¿Deseas activar el acceso biométrico?",
                    color = MaterialTheme.colorScheme.surfaceDim,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        when (canAuthenticate) {
                            true -> biometricPrompt?.authenticate(promptInfo)
                            false -> Toast.makeText(context, "Biometría no disponible", Toast.LENGTH_SHORT).show()
                            null -> Toast.makeText(context, "Verificando biometría...", Toast.LENGTH_SHORT).show()
                        }
                    },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .height(42.dp)
                ) {
                    Text(
                        text = "Activar",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            LaunchedEffect(visible) {
                if (visible) {
                    delay(300) // Tiempo para que el teclado termine de cerrarse
                    startAnimation = true
                } else {
                    startAnimation = false
                }
            }

            val scale by animateFloatAsState(
                targetValue = if (startAnimation) 1f else 0f,
                animationSpec = spring(
                    dampingRatio = 0.25f,
                    stiffness = 300f
                ),
                label = ""
            )

            if (visible) {
                Icon(
                    imageVector = Icons.Filled.CheckCircleOutline,
                    contentDescription = "Verified",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier
                        .size(128.dp)
                        .scale(scale)
                )
            }
        }
    }
}

// Extensión para obtener FragmentActivity de manera segura
fun Context.findFragmentActivity(): FragmentActivity? = when (this) {
    is FragmentActivity -> this
    is ContextWrapper -> baseContext.findFragmentActivity()
    else -> null
}

@Preview(
    showBackground = true
)

@Composable
fun ViewAuthenticationBiometric(){

    val context = LocalContext.current.applicationContext
    val repository = remember { SetupRepository(context) }
    val vm = remember { SetupIntoViewModel(repository) }

    LlaveElectronicaTheme {
        AppBackground {
            AuthenticationBiometric(
                viewModel = vm
            )
        }
    }
}
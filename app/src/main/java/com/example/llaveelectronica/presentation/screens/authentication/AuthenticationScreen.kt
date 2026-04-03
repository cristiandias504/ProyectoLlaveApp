package com.example.llaveelectronica.presentation.screens.authentication

import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.llaveelectronica.R
import com.example.llaveelectronica.data.repository.SetupRepository
import com.example.llaveelectronica.presentation.screens.setupIntoScreen.SetupIntoViewModel
import com.example.llaveelectronica.ui.components.AppBackground
import com.example.llaveelectronica.ui.components.Authentication
import com.example.llaveelectronica.ui.components.findFragmentActivity
import com.example.llaveelectronica.ui.theme.LlaveElectronicaTheme

@Composable
fun AuthenticationScreen(
    onClick: () -> Unit,
    viewModel: SetupIntoViewModel
) {
    val authenticationScreenViewModel by viewModel.setupIntoState

    var optionPin by remember { mutableStateOf(false) }

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
                    onClick()
                }
            }
        )
    }

    // Verificar hardware y datos biométricos
    LaunchedEffect(Unit) {

        viewModel.validatePin("1")

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 70.dp),
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 24.dp)
        ) {

            // Logo
            Image(
                painter = painterResource(R.mipmap.logo),
                contentDescription = null,
                modifier = Modifier.size(128.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surfaceDim)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Título principal
            Text(
                text = "Mobile Access Key",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.surfaceDim,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height((32.dp)))

            Text(
                text = "Bienvenido",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )

            if (authenticationScreenViewModel.isAuthenticationBiometricActive) {
                if (!optionPin) {
                    viewModel.validatePin("0000")
                    Spacer(modifier = Modifier.height(200.dp))
                    Icon(
                        imageVector = Icons.Filled.Fingerprint,
                        contentDescription = "Authentication fingerprint",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .size(64.dp)
                            .clickable {
                                when (canAuthenticate) {
                                    true -> biometricPrompt?.authenticate(promptInfo)
                                    false -> Toast.makeText(
                                        context,
                                        "Biometría no disponible",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    null -> Toast.makeText(
                                        context,
                                        "Verificando biometría...",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    )
                } else {
                    Spacer(modifier = Modifier.height(16.dp))
                    Authentication(viewModel, isActive = true)
                }
            } else {
                Spacer(modifier = Modifier.height(16.dp))
                Authentication(viewModel, isActive = true)
            }
        }

        // Botón
        Button(
            onClick = {
                if (authenticationScreenViewModel.isAuthenticationBiometricActive){
                    if (!optionPin) {
                        optionPin = true
                        viewModel.validatePin("1")
                    }
                    else {
                        if (authenticationScreenViewModel.isPinValid) {
                            onClick()
                        } else {
                            viewModel.validatePinFail()
                        }
                    }
                } else {
                    if (authenticationScreenViewModel.isPinValid) {
                        onClick()
                    } else {
                        viewModel.validatePinFail()
                    }
                }
            },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            enabled = authenticationScreenViewModel.isPinComplete,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 140.dp)
                .height(42.dp)
        ) {
            Text(
                text =
                    if (authenticationScreenViewModel.isAuthenticationBiometricActive){
                        if (!optionPin) "Continuar con pin" else "Ingresar"
                    } else "Ingresar",
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun ViewAuthenticationScreen() {

    val context = LocalContext.current.applicationContext
    val repository = remember { SetupRepository(context) }
    val vm = remember { SetupIntoViewModel(repository) }

    LlaveElectronicaTheme {
        AppBackground {
            AuthenticationScreen(
                onClick = {},
                viewModel = vm
            )
        }
    }
}
package com.example.llaveelectronica.presentation.screens.authentication

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.llaveelectronica.data.SetupRepository
import com.example.llaveelectronica.presentation.screens.setupIntoScreen.SetupIntoViewModel
import com.example.llaveelectronica.ui.components.AppBackground
import com.example.llaveelectronica.ui.theme.LlaveElectronicaTheme

@Composable
fun AuthenticationScreen(
    viewModel: SetupIntoViewModel
) {

    Text(
        text = "Ventana de Autenticación"
    )
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
                viewModel = vm
            )
        }
    }
}
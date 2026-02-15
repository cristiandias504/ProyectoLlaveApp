package com.example.llaveelectronica.ui.components

import android.Manifest
import android.R.attr.data
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.llaveelectronica.presentation.screens.setupIntoScreen.SetupIntoViewModel
import com.example.llaveelectronica.ui.theme.LlaveElectronicaTheme
import android.provider.Settings


//fun openAppSettings(context: Context) {
//
//    val intent = Intent(
//        Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//    ).apply {
//        data = Uri.fromParts("package", context.packageName, null)
//    }
//
//    context.startActivity(intent)
//}

@Composable
fun Permissions(
    viewModel: SetupIntoViewModel = viewModel(),
) {
    val uiPermissions by viewModel.setupIntoState
    val context = LocalContext.current

    // Lista de permisos a pedir
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->

        val bluetoothGranted =
            permissions[Manifest.permission.BLUETOOTH_CONNECT] ?: true

        val notificationsGranted =
            permissions[Manifest.permission.POST_NOTIFICATIONS] ?: true

        val allGranted = bluetoothGranted && notificationsGranted

        viewModel.requestPermissions(allGranted)
    }

//    if(!uiPermissions.permissionsGranted) {
//        SelectTheme()
//        Button(
//            onClick = {
//                openAppSettings(context)
//            }
//        ) {
//            Text("Ir a Configuración")
//        }
//    }





    LaunchedEffect(Unit) {

        val permissionsToRequest = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.BLUETOOTH_CONNECT)
        }

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        if (permissionsToRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionsToRequest.toTypedArray())
        } else {
            viewModel.requestPermissions(true)
        }
    }

    // UI (solo informativa)
    Box(
        modifier = Modifier
            .height(350.dp)
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Solicitando permisos…",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.surfaceDim
            )

            Spacer(modifier = Modifier.height(12.dp))

            CircularProgressIndicator()
        }
    }
}

@Preview(
    showBackground = true
)

@Composable
fun ViewPermissions(){
    LlaveElectronicaTheme {
        AppBackground {
            Permissions()
        }
    }
}
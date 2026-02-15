package com.example.llaveelectronica.ui.components

//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.llaveelectronica.presentation.screens.SetupIntoScreen.SetupIntoViewModel
//import com.example.llaveelectronica.ui.theme.LlaveElectronicaTheme
//
//@Composable
//fun Permissions (
//    viewModel: SetupIntoViewModel = viewModel()
//) {
//    Box(
//        modifier = Modifier
//            //.fillMaxWidth()
//            .height(350.dp)
//            .padding(horizontal = 8.dp),
//        contentAlignment = Alignment.TopCenter
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier
//                .fillMaxWidth(),
//        ) {
////            val progress by viewModel.progress
////
////            val animatedProgress by animateFloatAsState(
////                targetValue = progress,
////                animationSpec = tween(400),
////                label = "ProgressAnim"
////            )
////
////            LinearProgressIndicator(
////                progress = {animatedProgress },
////                modifier = Modifier
////                    .fillMaxWidth()
////                    .height(16.dp)
////                    .clip(RoundedCornerShape(50)),
////                color = MaterialTheme.colorScheme.scrim, //primaryContainer,
////                trackColor = MaterialTheme.colorScheme.secondaryContainer,
////            )
////
////            Spacer(modifier = Modifier.height(24.dp))
//
//            Text(
//                text = "Permisos",
//                color = MaterialTheme.colorScheme.onPrimary,
//                style = MaterialTheme.typography.titleLarge
//            )
//
//            Spacer(modifier = Modifier.height(14.dp))
//        }
//    }
//}

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Permissions() {

    // Lista de permisos a pedir
    val permissions = remember {
        mutableStateListOf(
            Manifest.permission.BLUETOOTH_CONNECT
        )
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (!permissions.contains(Manifest.permission.POST_NOTIFICATIONS)) {
            permissions.add(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    // Launcher para permisos
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        result.forEach { (permission, granted) ->
            if (granted) {
                println("✅ Permiso concedido: $permission")
            } else {
                println("❌ Permiso denegado: $permission")
            }
        }
    }

    // 🚀 Se ejecuta cuando el composable entra en pantalla
    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            permissions.toTypedArray()
        )
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
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.height(12.dp))

            CircularProgressIndicator()
        }
    }
}


//@Preview(
//    showBackground = true
//)
//
//@Composable
//fun ViewPermissions(){
//    LlaveElectronicaTheme() {
//        AppBackground {
//            Permissions()
//        }
//    }
//}
package com.example.llaveelectronica.presentation.screens.mainScreen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.llaveelectronica.R
import com.example.llaveelectronica.data.repository.SetupRepository
import com.example.llaveelectronica.data.service.ConnectionService
import com.example.llaveelectronica.presentation.screens.setupIntoScreen.SetupIntoViewModel
import com.example.llaveelectronica.ui.components.AppBackground
import com.example.llaveelectronica.ui.theme.LlaveElectronicaTheme
import kotlinx.coroutines.delay

private const val TAG = "MainScreenUI"

@Composable
fun MainScreenUI(
    viewModel: SetupIntoViewModel
) {
    val state by viewModel.setupIntoState
    val context = LocalContext.current

    var motorcycleStatus by remember { mutableIntStateOf(0) }

    val animatedProgress by animateFloatAsState(
        targetValue = if (motorcycleStatus == 0 || motorcycleStatus == 2) 1f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "progressAnimation"
    )

    // Dispara la animación
    LaunchedEffect(Unit) {
        delay(100)
        enviarBroadcast(context, "Verificación de estado")
    }

    // Configuración del Receptor de Broadcast
    DisposableEffect(context) {
        val receptorMensaje = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val mensaje = intent?.getStringExtra("Mensaje") ?: "Sin mensaje"
                //Log.d(TAG, "Desde ServicioConexion: $mensaje")

                when (mensaje) {
                    "Servicio iniciado correctamente" -> motorcycleStatus = 1
                    "Conexion establecida Correctamente" -> motorcycleStatus = 2
                    "Desconectado, Reiniciando conexion" -> motorcycleStatus = 1
                    "Conexión Bluetooth finalizada" -> motorcycleStatus = 0
                    "Respuesta Verificación de estado = false" -> motorcycleStatus = 1
                    "Respuesta Verificación de estado = true" -> motorcycleStatus = 2
                    "301Y" -> {}
                    "302Y" -> {}
                }
            }
        }
        val filter = IntentFilter("com.example.pruebaconexion.BroadCast")
        ContextCompat.registerReceiver(context, receptorMensaje, filter, ContextCompat.RECEIVER_NOT_EXPORTED)

        onDispose {
            context.unregisterReceiver(receptorMensaje)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        //verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically)
        {
            Column {
                Text(
                    text = "Bienvenido",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                //Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = state.nombre + " " + state.apellido,
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }


        // 2. Este Spacer con peso empuja la tarjeta hacia abajo
        Spacer(modifier = Modifier.weight(1f))

        Text(
            //text = state.modelo,
            text = "Motocicleta",
            fontSize = 32.sp,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Tarjeta del Vehículo Seleccionado
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(bottom = 24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(
                topStart = 32.dp,
                topEnd = 32.dp,
                bottomStart = 10.dp,
                bottomEnd = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color(0xFF3E5559)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 30.dp),
                    text = when (motorcycleStatus) {
                        0 -> "Desconectado"
                        1 -> "Buscando..."
                        2 -> "Conectado"
                        else -> "Error"
                    },
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                //Spacer(modifier = Modifier.height(16.dp))

                // Imagen del vehículo (Usando launcher icon como placeholder)
                Image(
                    painter = painterResource(R.mipmap.ktmduke390g3),
                    contentDescription = null,
                    modifier = Modifier
                        .size(260.dp)
                        .padding(8.dp)
                )

                //Spacer(modifier = Modifier.height(8.dp))

                Text(
                    //text = state.modelo,
                    text = "390 Duke G3",
                    fontSize = 32.sp,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                // BARRA DE CARGA
                if (motorcycleStatus == 1){
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(24.dp),
                        color = when (motorcycleStatus) {
                            0 -> Color(0xFFE53935)
                            1 -> Color(0xFFFF7518)
                            2 -> Color(0xFF2ECC71)
                            else -> Color(0xFFE53935)
                        },
                        trackColor = Color.Transparent, // Fondo transparente
                        strokeCap = StrokeCap.Butt // Sin bordes redondeados
                    )
                } else {
                    LinearProgressIndicator(
                        //progress = { if (motorcycleStatus == 0) animatedProgress else 1f },
                        progress = { animatedProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(24.dp),
                        color = when (motorcycleStatus) {
                            0 -> Color(0xFFE53935)
                            1 -> Color(0xFFFF7518)
                            2 -> Color(0xFF2ECC71)
                            else -> Color(0xFFE53935)
                        },
                        trackColor = Color.Transparent, // Fondo transparente
                        strokeCap = StrokeCap.Butt // Sin bordes redondeados
                    )
                }
            }
        }

        Row (
            modifier = Modifier
                .padding(bottom = 32.dp)
                .padding(horizontal = 4.dp)
                .fillMaxWidth()
                .navigationBarsPadding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Box(
                modifier = Modifier
                    .size(50.dp) // Tamaño del círculo
                    .clip(CircleShape) // Corta el contenido en forma de círculo
                    //.background(MaterialTheme.colorScheme.primary), // Color de fondo
                    .background(when (motorcycleStatus) {
                        0 -> Color(0xFFBDBDBD)
                        1 -> Color(0xFFBDBDBD)
                        2 -> Color(0xFF2ECC71)
                        else -> Color(0xFFE53935)
                    }),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PowerSettingsNew,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }

            Button(
                onClick = {
                    val intent = Intent(context, ConnectionService::class.java)
                    when (motorcycleStatus){
                        0 -> context.startForegroundService(intent)
                        1 -> context.stopService(intent)
                        2 -> context.stopService(intent)
                    }
                },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                enabled = true,
                modifier = Modifier.height(48.dp)
            ) {
                Text(
                    text = when(motorcycleStatus){
                        0 -> "Activar llave"
                        1 -> "Desactivar llave"
                        2 -> "Desconectar"
                        else -> "Error"
                    },
                    color = Color.White,
                    fontSize = 24.sp
                )
            }

            Box(
                modifier = Modifier
                    .size(50.dp) // Tamaño del círculo
                    .clip(CircleShape) // Corta el contenido en forma de círculo
                    //.background(MaterialTheme.colorScheme.primary), // Color de fondo
                    .background(when (motorcycleStatus) {
                        0 -> Color(0xFFBDBDBD)
                        1 -> Color(0xFFBDBDBD)
                        2 -> Color(0xFF2ECC71)
                        else -> Color(0xFFE53935)
                    }),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.NotificationsActive,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }

//            Icon(
//                imageVector = Icons.Default.NotificationsActive,
//                contentDescription = null,
//                tint = Color.White,
//                modifier = Modifier.size(48.dp)
//            )
        }

        // Botón
//        Button(
//            onClick = {
//                val intent = Intent(context, ConnectionService::class.java)
//                when (motorcycleStatus){
//                    0 -> context.startForegroundService(intent)
//                    1 -> context.stopService(intent)
//                    2 -> context.stopService(intent)
//                }
//            },
//            shape = RoundedCornerShape(50),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = MaterialTheme.colorScheme.primary
//            ),
//            enabled = true,
//            modifier = Modifier
//                .padding(bottom = 32.dp)
//                .navigationBarsPadding()
//                .height(48.dp)
//        ) {
//            Text(
//                text = when(motorcycleStatus){
//                    0 -> "Activar llave"
//                    1 -> "Desactivar llave"
//                    2 -> "Desconectar"
//                    else -> "Error"
//                },
//                color = Color.White,
//                fontSize = 24.sp
//            )
//        }
    }
}


// Enviar Mensaje por broadcast
private fun enviarBroadcast(context: Context, mensaje: String) {
    val enviarBroadcast = Intent("com.example.pruebaconexion.BroadCast").apply {
        setPackage(context.packageName)
        putExtra("Mensaje", mensaje)
    }
    context.sendBroadcast(enviarBroadcast)
    Log.d(TAG, "Enviando a Servicio: $mensaje")
}

@Preview(showBackground = true)
@Composable
fun ViewMainScreenUI() {
    val context = LocalContext.current.applicationContext
    val repository = remember { SetupRepository(context) }
    val vm = remember { SetupIntoViewModel(repository) }

    LlaveElectronicaTheme {
        AppBackground {
            MainScreenUI(viewModel = vm)
        }
    }
}
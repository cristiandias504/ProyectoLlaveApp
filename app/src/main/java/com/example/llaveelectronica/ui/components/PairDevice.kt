package com.example.llaveelectronica.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.VolumeDown
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.llaveelectronica.R
import com.example.llaveelectronica.data.repository.SetupRepository
import com.example.llaveelectronica.presentation.screens.setupIntoScreen.SetupIntoViewModel
import com.example.llaveelectronica.ui.theme.LlaveElectronicaTheme

@Composable
fun PairDevice(
    viewModel: SetupIntoViewModel
) {
    val pairDeviceViewModel by viewModel.setupIntoState

    val modelosConImagen = mapOf(
        "Duke 200 G1" to R.mipmap.ktmduke200g1,
        "Duke 390 G1" to R.mipmap.ktmduke390g1,
        "Duke 390 G3" to R.mipmap.ktmduke390g3,
    )

    var expandedMarca by remember { mutableStateOf(false) }
    var expandedModelo by remember { mutableStateOf(false) }

    var modeloSeleccionado by remember { mutableStateOf("Seleccionar") }

    LaunchedEffect(Unit) {
        viewModel.stateButton(false)
    }

    Box(
        modifier = Modifier
            //.fillMaxWidth()
            .height(350.dp)
            .padding(horizontal = 8.dp), contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Vincular Motocicleta",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Dispositivos disponibles",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {


                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color(0xFF8FD3E8))
                                    .clickable { expandedMarca = true }
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.TwoWheeler,
                                    contentDescription = null,
                                    tint = Color.Black
                                )

                                Text(
                                    text = pairDeviceViewModel.marca,
                                    color = Color.Black,
                                    style = MaterialTheme.typography.bodyLarge
                                )

                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                            }

                            // Menú desplegable
                            DropdownMenu(
                                expanded = expandedMarca,
                                onDismissRequest = { expandedMarca = false }) {
                                pairDeviceViewModel.marcasDisponibles.forEach { marca ->
                                    DropdownMenuItem(text = { Text(marca) }, onClick = {
                                        viewModel.stateButton(false)
                                        viewModel.onMarcaSelected(marca)
                                        expandedMarca = false
                                        modeloSeleccionado = ""
                                    })
                                }
                            }
                        }

                        Box(modifier = Modifier.padding(8.dp)) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color(0xFF8FD3E8))
                                    .clickable { expandedModelo = true }
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = pairDeviceViewModel.modelo,
                                    color = Color.Black,
                                    style = MaterialTheme.typography.bodyLarge
                                )

                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                            }

                            // Menú desplegable
                            DropdownMenu(
                                expanded = expandedModelo,
                                onDismissRequest = { expandedModelo = false }) {
                                pairDeviceViewModel.modelosDisponibles.forEach { modelo ->
                                    DropdownMenuItem(text = { Text(modelo) }, onClick = {
                                        viewModel.onModeloSelected(modelo)
                                        modeloSeleccionado = modelo
                                        expandedModelo = false
                                    })
                                }
                            }
                        }
                    }


                }
            }

            val imagenSeleccionada = modelosConImagen[modeloSeleccionado]

            if (imagenSeleccionada != null) {
                Image(
                    painter = painterResource(imagenSeleccionada),
                    contentDescription = null,
                    modifier = Modifier.size(160.dp)
                )
            }
        }
    }
}

@Preview(
    showBackground = true
)

@Composable
fun ViewPairDevice() {

    val context = LocalContext.current.applicationContext
    val repository = remember { SetupRepository(context) }
    val vm = remember { SetupIntoViewModel(repository) }

    LlaveElectronicaTheme {
        AppBackground {
            PairDevice(
                viewModel = vm,
            )
        }
    }
}
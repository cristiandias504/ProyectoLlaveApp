package com.example.llaveelectronica.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.llaveelectronica.presentation.screens.setupIntoScreen.SetupIntoViewModel
import com.example.llaveelectronica.ui.components.AppBackground
import com.example.llaveelectronica.ui.theme.LlaveElectronicaTheme

@Composable
fun DataPresentationScreen(
    viewModel: SetupIntoViewModel
) {
    val dataPresentationViewModel by viewModel.setupIntoState

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp)
            .padding(horizontal = 16.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Tema Automatico: ",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.surfaceDim,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.width(14.dp))

                Text(
                    text = dataPresentationViewModel.autoTheme.toString(),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.surfaceDim,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Tema: ",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.surfaceDim,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.width(14.dp))

                Text(
                    text = if (dataPresentationViewModel.selectedThemeDark) "Oscuro"
                            else ("Claro"),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.surfaceDim,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Nombre: ",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.surfaceDim,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.width(14.dp))

                Text(
                    text = dataPresentationViewModel.nombre,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.surfaceDim,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Apellido: ",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.surfaceDim,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.width(14.dp))

                Text(
                    text = dataPresentationViewModel.apellido,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.surfaceDim,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Celular: ",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.surfaceDim,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.width(14.dp))

                Text(
                    text = dataPresentationViewModel.celular,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.surfaceDim,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Permisos concedidos: ",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.surfaceDim,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.width(14.dp))

                Text(
                    text = dataPresentationViewModel.permissionsGranted.toString(),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.surfaceDim,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Pin: ",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.surfaceDim,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.width(14.dp))

                Text(
                    text = dataPresentationViewModel.pin,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.surfaceDim,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Marca: ",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.surfaceDim,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.width(14.dp))

                Text(
                    text = dataPresentationViewModel.marca,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.surfaceDim,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Modelo: ",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.surfaceDim,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.width(14.dp))

                Text(
                    text = dataPresentationViewModel.modelo,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.surfaceDim,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

        }
    }
}


@Preview (
    showBackground = true
)
@Composable
fun ViewDataPresentationScreen(){
    LlaveElectronicaTheme{
        AppBackground {
            DataPresentationScreen(viewModel = viewModel())
        }
    }
}
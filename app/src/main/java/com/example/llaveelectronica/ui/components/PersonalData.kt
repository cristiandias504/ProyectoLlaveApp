package com.example.llaveelectronica.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.llaveelectronica.ui.theme.LlaveElectronicaTheme

@Composable
fun PersonalData (
    //viewModel: SetupIntoViewModel = viewModel()
) {
    Box(
        modifier = Modifier
            //.fillMaxWidth()
            .height(350.dp)
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = "Tus Datos",
                color = MaterialTheme.colorScheme.surfaceDim,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    //.padding(horizontal = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .width(32.dp)
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        tint = MaterialTheme.colorScheme.surfaceDim,
                        contentDescription = "User",
                        modifier = Modifier.size(32.dp),
                    )

                    Spacer(modifier = Modifier.height(80.dp))

                    Icon(
                        imageVector = Icons.Filled.Call,
                        tint = MaterialTheme.colorScheme.surfaceDim,
                        contentDescription = "Call",
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    var nombre by remember { mutableStateOf("") }
                    var apellido by remember { mutableStateOf("") }
                    var telefono by remember { mutableStateOf("") }

                    TextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre", color = MaterialTheme.colorScheme.surfaceDim) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceDim,
                            focusedTextColor = MaterialTheme.colorScheme.surfaceDim,
                            unfocusedTextColor = MaterialTheme.colorScheme.surfaceDim,
                        )
                    )

                    TextField(
                        value = apellido,
                        onValueChange = { apellido = it },
                        label = { Text("Apellido", color = MaterialTheme.colorScheme.surfaceDim) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceDim,
                            focusedTextColor = MaterialTheme.colorScheme.surfaceDim,
                            unfocusedTextColor = MaterialTheme.colorScheme.surfaceDim,
                        )
                    )

                    TextField(
                        value = telefono,
                        onValueChange = { telefono = it},
                        label = { Text("Teléfono", color = MaterialTheme.colorScheme.surfaceDim) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceDim,
                            focusedTextColor = MaterialTheme.colorScheme.surfaceDim,
                            unfocusedTextColor = MaterialTheme.colorScheme.surfaceDim,
                        )
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true
)

@Composable
fun ViewPersonalData(){
    LlaveElectronicaTheme {
        AppBackground {
            PersonalData()
        }
    }
}
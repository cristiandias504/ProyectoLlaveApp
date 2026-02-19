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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.llaveelectronica.presentation.screens.setupIntoScreen.SetupIntoViewModel
import com.example.llaveelectronica.ui.theme.LlaveElectronicaTheme

@Composable
fun PersonalData (
    viewModel: SetupIntoViewModel
) {

    LaunchedEffect(Unit) {
        viewModel.stateButton(false)
    }

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

                val focusRequesterApellido = remember { FocusRequester() }
                val focusRequesterTelefono = remember { FocusRequester() }
                val focusManager = LocalFocusManager.current

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    var nombre by remember { mutableStateOf("") }
                    var apellido by remember { mutableStateOf("") }
                    var telefono by remember { mutableStateOf("") }

                    TextField(
                        value = nombre,
                        onValueChange = {
                            nombre = it
                            viewModel.registerNombre(it)
                        },
                        label = { Text("Nombre", color = MaterialTheme.colorScheme.surfaceDim) },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusRequesterApellido.requestFocus()
                            }
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceDim,
                            focusedTextColor = MaterialTheme.colorScheme.surfaceDim,
                            unfocusedTextColor = MaterialTheme.colorScheme.surfaceDim,
                        ),
                        modifier = Modifier.focusRequester(FocusRequester.Default),
                    )

                    TextField(
                        value = apellido,
                        onValueChange = {
                            apellido = it
                            viewModel.registerApellido(it)
                        },
                        label = { Text("Apellido", color = MaterialTheme.colorScheme.surfaceDim) },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusRequesterTelefono.requestFocus()
                            }
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceDim,
                            focusedTextColor = MaterialTheme.colorScheme.surfaceDim,
                            unfocusedTextColor = MaterialTheme.colorScheme.surfaceDim,
                        ),
                        modifier = Modifier.focusRequester((focusRequesterApellido))
                    )

                    TextField(
                        value = telefono,
                        onValueChange = { newValue ->
                            val filtered = newValue
                                .filter { it.isDigit() }
                                .take(10)

                            val corrected = when {
                                filtered.isEmpty() -> ""
                                filtered.first() != '3' -> "3" +filtered.drop(1)
                                else -> filtered
                            }

                            telefono = corrected
                            viewModel.registerCelular(corrected)
                        },
                        label = { Text("Teléfono", color = MaterialTheme.colorScheme.surfaceDim) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                            }
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceDim,
                            focusedTextColor = MaterialTheme.colorScheme.surfaceDim,
                            unfocusedTextColor = MaterialTheme.colorScheme.surfaceDim,
                        ),
                        modifier = Modifier.focusRequester(focusRequesterTelefono)
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
            PersonalData(
                viewModel = viewModel()
            )
        }
    }
}
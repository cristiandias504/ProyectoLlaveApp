package com.example.llaveelectronica.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.llaveelectronica.presentation.screens.setupIntoScreen.SetupIntoViewModel
import com.example.llaveelectronica.ui.theme.LlaveElectronicaTheme

@Composable
fun SelectTheme (
    viewModel: SetupIntoViewModel = viewModel()
) {
    Box(
        modifier = Modifier
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
                text = "Tema de la aplicación",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(24.dp)
            ) {
                Text(
                    text = "Automático",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyLarge
                )
                var automatico by remember { mutableStateOf(false) }

                RadioButton(
                    selected = automatico,
                    onClick = { automatico = !automatico },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }

            Spacer(modifier = Modifier.height(14.dp))
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color(0xFF3E5559)), // gris verdoso
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .offset(y = 32.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .width(110.dp)
                            .height(180.dp)
                            .padding(horizontal = 0.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colorScheme.inverseSurface), // gris verdoso
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier

                                .fillMaxSize()
                                .padding(6.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(MaterialTheme.colorScheme.onPrimary), // gris verdoso
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Column(
                                modifier = Modifier.padding(top = 5.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)              // tamaño del círculo
                                        .background(Color.Black, shape = CircleShape)  // color y forma de círculo
                                ) {}
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .width(110.dp)
                            .height(180.dp)
                            .padding(horizontal = 0.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colorScheme.inverseSurface), // gris verdoso
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier

                                .fillMaxSize()
                                .padding(6.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(MaterialTheme.colorScheme.scrim), // gris verdoso
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Column(
                                modifier = Modifier.padding(top = 5.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)              // tamaño del círculo
                                        .background(Color.White, shape = CircleShape)  // color y forma de círculo
                                ) {}
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            var selected by remember { mutableStateOf("Oscuro") }

            Row (
                horizontalArrangement = Arrangement.spacedBy(80.dp)
            ) {
                listOf("Claro", "Oscuro").forEach { option ->
                    Column {
                        Text(
                            text = option,
                            color = MaterialTheme.colorScheme.onPrimary)
                        RadioButton(
                            selected = selected == option,
                            onClick = { selected = option},
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.onPrimary,
                                unselectedColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )

                    }
                }
            }
        }
        
    }
}

@Preview(
    showBackground = true
)
@Composable
fun ViewSelectTheme(){
    LlaveElectronicaTheme {
        AppBackground {
            SelectTheme()
        }
    }
}
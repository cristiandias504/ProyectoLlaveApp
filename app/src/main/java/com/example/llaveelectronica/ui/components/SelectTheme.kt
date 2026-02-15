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
import androidx.compose.ui.R
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.llaveelectronica.presentation.screens.setupIntoScreen.SetupIntoViewModel
import com.example.llaveelectronica.ui.theme.LlaveElectronicaTheme

@Composable
fun SelectTheme (
    viewModel: SetupIntoViewModel
) {
    val selectThemeViewModel by viewModel.setupIntoState

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
                color = MaterialTheme.colorScheme.surfaceDim,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(24.dp)
            ) {
                Text(
                    text = "Automático",
                    color = MaterialTheme.colorScheme.surfaceDim,
                    style = MaterialTheme.typography.bodyLarge
                )

                RadioButton(
                    selected = selectThemeViewModel.autoTheme,
                    onClick = {viewModel.onThemeChange(
                        newAutoTheme = !selectThemeViewModel.autoTheme,
                        newTheme = selectThemeViewModel.selectedThemeDark)},
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.surfaceDim,
                        unselectedColor = MaterialTheme.colorScheme.surfaceDim
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
                    .alpha(
                        if(selectThemeViewModel.autoTheme) 0.3f
                        else 1.0f
                    )
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
                            .background(Color(0xFF2B3133)),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier

                                .fillMaxSize()
                                .padding(6.dp)
                                .clip(RoundedCornerShape(14.dp))
                                //.background(MaterialTheme.colorScheme.onPrimary),
                                .background(
                                    brush = Brush.verticalGradient(
                                        colorStops = arrayOf(
                                            0.0f to Color(0xFF006874),
                                            0.10f to Color(0xFF006874),
                                            0.65f to Color(0xFFFFFFFF)
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Column(
                                modifier = Modifier.padding(top = 5.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(Color.Black, shape = CircleShape)
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
                            .background(Color(0xFF2B3133)),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier

                                .fillMaxSize()
                                .padding(6.dp)
                                .clip(RoundedCornerShape(14.dp))
                                //.background(MaterialTheme.colorScheme.scrim), // gris verdoso
                                .background(
                                    brush = Brush.verticalGradient(
                                        colorStops = arrayOf(
                                            0.0f to Color(0xFF006874),
                                            0.10f to Color(0xFF006874),
                                            0.65f to Color(0xFF000000)
                                        )
                                    )
                                ),
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
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            var selected by remember { mutableStateOf("Oscuro") }

            Row (
                horizontalArrangement = Arrangement.spacedBy(80.dp),
                modifier = Modifier
                    .alpha(
                        if(selectThemeViewModel.autoTheme) 0.3f
                        else 1.0f
                    )
            ) {
                listOf("Claro", "Oscuro").forEach { option ->
                    Column {
                        Text(
                            text = option,
                            color = MaterialTheme.colorScheme.surfaceDim)
                        RadioButton(
                            enabled = !selectThemeViewModel.autoTheme,
                            selected = selected == option,
                            onClick = {
                                selected = option

                                if (option == "Oscuro") {
                                   viewModel.onThemeChange(newTheme = true)

                                } else {
                                    viewModel.onThemeChange(newTheme = false)
                                }
                                      },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.surfaceDim,
                                unselectedColor = MaterialTheme.colorScheme.surfaceDim,
                                disabledSelectedColor = MaterialTheme.colorScheme.surfaceDim,
                                disabledUnselectedColor = MaterialTheme.colorScheme.surfaceDim
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
            SelectTheme(
                viewModel = viewModel()
            )
        }
    }
}
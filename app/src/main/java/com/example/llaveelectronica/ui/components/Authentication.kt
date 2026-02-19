package com.example.llaveelectronica.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.llaveelectronica.presentation.screens.setupIntoScreen.SetupIntoViewModel
import com.example.llaveelectronica.ui.theme.LlaveElectronicaTheme
import kotlinx.coroutines.delay


@Composable
fun Authentication (
    viewModel: SetupIntoViewModel,
    isActive: Boolean
) {
    var pin by remember { mutableStateOf("") }

    val authenticationViewModel by viewModel.setupIntoState
    val confirmation = authenticationViewModel.firstPinCompleteEvent
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current


    LaunchedEffect(isActive) {
        if (isActive) {
            viewModel.stateButton(false)
            delay(100)
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }

    Box(
        modifier = Modifier
            //.fillMaxWidth()
            .height(350.dp)
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .height(1.dp)
                .width(0.dp)
        ) {
            TextField(
                value = pin,
                onValueChange = { newValue ->

                    val filtered = newValue.filter { it.isDigit() }.take(4)

                    when {
                        filtered.length > pin.length -> {
                            val newDigit = filtered.last()
                            viewModel.registerPin(confirmation = confirmation, digit = newDigit.toString())
                        }
                        filtered.length < pin.length -> {
                            viewModel.registerPin(confirmation = confirmation, digit = "D")
                        }
                    }

                    pin = filtered

                    if (pin.length == 4) pin = ""
                },

                modifier = Modifier
                    .height(1.dp)
                    .width(0.dp)
                    .focusRequester(focusRequester),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                visualTransformation = VisualTransformation.None,
                singleLine = true,
                enabled = !authenticationViewModel.stateButton
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = "Autenticación",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(14.dp))

            val offsetX = remember { Animatable(0f) }
            val pinError = authenticationViewModel.pinError

            LaunchedEffect(pinError) {
                if (pinError) {
                    repeat(4) {
                        offsetX.animateTo(-20f, tween(50))
                        offsetX.animateTo(20f, tween(50))
                    }
                    offsetX.animateTo(0f)
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .offset(x = offsetX.value.dp)
                    .clickable {
                        // Abre el teclado al tocar el Row
                        focusRequester.requestFocus()
                        keyboardController?.show()
                        pin = ""
                        viewModel.registerPin(confirmation = confirmation, digit = "R")
                    }
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .border(
                            width = 2.dp,
                            color = if (pinError) Color.Red else Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (pin.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    CircleShape
                                )
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .border(
                            width = 2.dp,
                            color = if (pinError) Color.Red else Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (pin.length >= 2) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    CircleShape
                                )
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .border(
                            width = 2.dp,
                            color = if (pinError) Color.Red else Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (pin.length >= 3) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    CircleShape
                                )
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .border(
                            width = 2.dp,
                            color = if (pinError) Color.Red else Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (pin.length >= 4) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    CircleShape
                                )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            val visible = authenticationViewModel.stateButton
            var startAnimation by remember { mutableStateOf(false) }

            LaunchedEffect(visible) {
                if (visible) {
                    delay(300) // Tiempo para que el teclado termine de cerrarse
                    startAnimation = true
                } else {
                    startAnimation = false
                }
            }

            val scale by animateFloatAsState(
                targetValue = if (startAnimation) 1f else 0f,
                animationSpec = spring(
                    dampingRatio = 0.25f,
                    stiffness = 300f
                ),
                label = ""
            )

            if (visible) {
                Icon(
                    imageVector = Icons.Filled.CheckCircleOutline,
                    contentDescription = "Verified",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier
                        .size(128.dp)
                        .scale(scale)
                )
            }
        }
    }
}

@Preview(
    showBackground = true
)

@Composable
fun ViewAuthentication(){
    LlaveElectronicaTheme {
        AppBackground {
            Authentication(
                viewModel = viewModel(),
                isActive = true
            )
        }
    }
}
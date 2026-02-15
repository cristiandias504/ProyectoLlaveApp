package com.example.llaveelectronica.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.example.llaveelectronica.ui.theme.LlaveElectronicaTheme

@Composable
fun AppBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to MaterialTheme.colorScheme.primary,
                        0.10f to MaterialTheme.colorScheme.primary,
                        0.65f to MaterialTheme.colorScheme.scrim
                    )
                )
            ),
//            .background(
//                brush = Brush.verticalGradient(
//                    colorStops = arrayOf(
//                        0.0f to MaterialTheme.colorScheme.primary,
//                        0.10f to MaterialTheme.colorScheme.primary,
//                        0.65f to MaterialTheme.colorScheme.onPrimary
//                    )
//                )
//            ),
    ) {
        content ()
    }
}

@Preview (
    showBackground = true
)
@Composable
fun ViewAppBackground(){
    LlaveElectronicaTheme{
        AppBackground { }
    }
}
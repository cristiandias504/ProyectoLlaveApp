package com.example.llaveelectronica.presentation.screens.SplashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.llaveelectronica.R
import com.example.llaveelectronica.ui.theme.LlaveElectronicaTheme

@Composable
fun SplashScreenUI(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to MaterialTheme.colorScheme.primary,
                        0.10f to MaterialTheme.colorScheme.primary,
                        0.65f to MaterialTheme.colorScheme.scrim//.copy(alpha = 0.8f)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.mipmap.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(180.dp)
        )
    }
}

@Preview (
    showBackground = true
)
@Composable
fun viewSplashScreenUI(){
    LlaveElectronicaTheme{
        SplashScreenUI()
    }
}
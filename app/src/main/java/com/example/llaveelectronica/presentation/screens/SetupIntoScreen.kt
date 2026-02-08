package com.example.llaveelectronica.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.llaveelectronica.R
import com.example.llaveelectronica.ui.theme.LlaveElectronicaTheme

@Composable
fun SetupIntroScreen(
    onStartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 70.dp)
                .padding(horizontal = 24.dp)
        ) {

            // Logo
            Image(
                painter = painterResource(R.mipmap.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(128.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Título principal
            Text(
                text = "Mobile Access Key",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(215.dp))

            // Texto de Bienvenida
            Text(
                text = "Vamos a configurar la aplicación",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(60.dp))
        }

        // Botón
        Button(
            onClick = onStartClick,
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 140.dp)
                .height(42.dp)
        ) {
            Text(
                text = "Comenzar",
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Preview (
    showBackground = true
)
@Composable
fun viewSetupIntroScreen(){
    LlaveElectronicaTheme{
        SetupIntroScreen(
            onStartClick = {}
        )
    }
}
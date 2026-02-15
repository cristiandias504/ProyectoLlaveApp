package com.example.llaveelectronica.presentation.screens.welcomeScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.llaveelectronica.R
import com.example.llaveelectronica.ui.components.AppBackground
import com.example.llaveelectronica.ui.theme.LlaveElectronicaTheme
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreenUI(
    modifier: Modifier = Modifier,
    onStartClick: () -> Unit,
) {
    var animate by remember { mutableStateOf(false) }

    // Avisar cuando la animación terminó
    LaunchedEffect(animate) {
        if (animate) {
            delay(600) // duración de la animación
            onStartClick()
        }
    }

    val startSize = 180.dp
    val endSize = 128.dp

    val logoSize by animateDpAsState(
        targetValue = if (animate) endSize else startSize,
        animationSpec = tween(
            durationMillis = 600,
            easing = FastOutSlowInEasing
        ),
        label = "LogoSize"
    )

    val topPadding by animateDpAsState(
        targetValue = if (animate) 70.dp else 166.dp,
        animationSpec = tween(
            durationMillis = 600,
            easing = FastOutSlowInEasing
        ),
        label = "TopPadding"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = topPadding)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            Image(
                painter = painterResource(R.mipmap.logo),
                contentDescription = null,
                modifier = Modifier.size(logoSize)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Mobile Access Key",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(180.dp))

            AnimatedVisibility(
                visible = !animate,
                exit = fadeOut(animationSpec = tween(200))
            ) {
                Text(
                    text = "Bienvenido",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        Button(
            onClick = {
                if (!animate) animate = true
            },
            shape = RoundedCornerShape(50),
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
fun ViewWelcomeScreenUI(){
    LlaveElectronicaTheme{
        AppBackground {
            WelcomeScreenUI(
                onStartClick = {}
            )
        }
    }
}
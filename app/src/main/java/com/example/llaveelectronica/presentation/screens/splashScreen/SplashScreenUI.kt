package com.example.llaveelectronica.presentation.screens.splashScreen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.llaveelectronica.R
import com.example.llaveelectronica.ui.components.AppBackground
import com.example.llaveelectronica.ui.theme.LlaveElectronicaTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreenUI(
    modifier: Modifier = Modifier,
    onAnimationFinished: () -> Unit = {}
) {

    var animate by remember { mutableStateOf(false) }

    val windowInfo = LocalWindowInfo.current
    val screenHeight = with(LocalDensity.current) {
        windowInfo.containerSize.height.toDp()
    }

    // Dispara la animación
    LaunchedEffect(Unit) {
        delay(600)
        animate = true
    }

    // Avisar cuando la animación terminó
    LaunchedEffect(animate) {
        if (animate) {
            delay(800) // duración de la animación
            onAnimationFinished()
        }
    }

    val startSize = 200.dp
    val endSize = 180.dp

    val logoSize by animateDpAsState(
        targetValue = if (animate) endSize else startSize,
        animationSpec = tween(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        ),
        label = "LogoSize"
    )

    val logoOffsetY by animateDpAsState(
        targetValue = if (animate) {
            -(screenHeight / 2) + 166.dp + (endSize / 2)
        } else {
            0.dp
        },
        animationSpec = tween(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        ),
        label = "LogoOffset"
    )

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.mipmap.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(logoSize)
                .offset(y = logoOffsetY)
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun ViewSplashScreenUI(){
    LlaveElectronicaTheme{
        AppBackground {
            SplashScreenUI()
        }
    }
}
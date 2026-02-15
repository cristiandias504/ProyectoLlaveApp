package com.example.llaveelectronica.presentation.screens.splashScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun SplashScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    SplashScreenUI(
        onAnimationFinished = {
            navController.navigate("welcome") {
                popUpTo("splash") { inclusive = true }
                launchSingleTop = true
            }
        }
    )
    SplashScreenUI(modifier)
}
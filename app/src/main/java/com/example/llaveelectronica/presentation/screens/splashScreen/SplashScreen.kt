package com.example.llaveelectronica.presentation.screens.splashScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.example.llaveelectronica.presentation.screens.setupIntoScreen.SetupIntoViewModel

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SetupIntoViewModel
) {
    val state by viewModel.setupIntoState

    SplashScreenUI(
        onAnimationFinished = {
            // Se decide el destino basado en isSetupCompleted
            val destination = if (state.isSetupCompleted) "authentication" else "welcome"

            navController.navigate(destination) {
                popUpTo("splash") { inclusive = true }
                launchSingleTop = true
            }
        },
        isConfig = state.isSetupCompleted
    )
}
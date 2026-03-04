package com.example.llaveelectronica.presentation.screens.splashScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.llaveelectronica.presentation.screens.setupIntoScreen.SetupIntoViewModel

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SetupIntoViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.setupIntoState

    SplashScreenUI(
        onAnimationFinished = {
//            navController.navigate("welcome") {
//                popUpTo("splash") { inclusive = true }
//                launchSingleTop = true
//            }

            // Decidimos el destino basado en isSetupCompleted
            val destination = if (state.isSetupCompleted) "dataPresentation" else "welcome"

            navController.navigate(destination) {
                popUpTo("splash") { inclusive = true }
                launchSingleTop = true
            }
        }
    )
    SplashScreenUI(modifier)
}
package com.example.llaveelectronica.presentation.screens.SplashScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SplashScreenViewModel = viewModel(),
    modifier: Modifier = Modifier
) {

    // Observa el estado de navegación
    val shouldNavigate by viewModel.navigateToWelcome.collectAsState()

    if (shouldNavigate) {
        navController.navigate("welcome") {
            popUpTo("splash") { inclusive = true }
        }
    }

    // UI
    SplashScreenUI(modifier)
}
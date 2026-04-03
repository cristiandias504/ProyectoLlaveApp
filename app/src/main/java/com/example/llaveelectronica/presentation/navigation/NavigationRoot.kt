package com.example.llaveelectronica.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.llaveelectronica.presentation.screens.authentication.AuthenticationScreen
import com.example.llaveelectronica.presentation.screens.mainScreen.MainScreenUI
import com.example.llaveelectronica.presentation.screens.setupIntoScreen.SetupIntoViewModel
import com.example.llaveelectronica.presentation.screens.setupIntoScreen.SetupIntroScreen
import com.example.llaveelectronica.presentation.screens.splashScreen.SplashScreen
import com.example.llaveelectronica.presentation.screens.welcomeScreen.WelcomeScreen

@Composable
fun NavigationRoot(setupIntoViewModel: SetupIntoViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") { SplashScreen(navController, setupIntoViewModel) }
        composable ("welcome") { WelcomeScreen(onStartClick = {navController.navigate("setupInto")} ) }
        composable ("setupInto") { SetupIntroScreen(onClick = {navController.navigate("mainScreenUI")}, viewModel = setupIntoViewModel) }
        composable ("authentication") { AuthenticationScreen(onClick = {navController.navigate("mainScreenUI")}, viewModel = setupIntoViewModel) }
        composable ("mainScreenUI") { MainScreenUI(viewModel = setupIntoViewModel) }
        //composable ("dataPresentation") { DataPresentationScreen(viewModel = setupIntoViewModel) }
    }
}
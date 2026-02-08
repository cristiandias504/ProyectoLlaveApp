package com.example.llaveelectronica.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.llaveelectronica.presentation.screens.SetupIntroScreen
import com.example.llaveelectronica.presentation.screens.SplashScreen.SplashScreen
import com.example.llaveelectronica.presentation.screens.WelcomeScreen.WelcomeScreen


//@Composable
//fun NavigationRoot() {
//    val navController = rememberNavController()
//
//    NavHost(
//        navController = navController,
//        startDestination = "splash"
//    ) {
//        composable("splash") { SplashScreen(navController) }
//        composable("welcome") { WelcomeScreen(onStartClick = {navController.navigate("setupInto")} ) }
//        composable("setupInto") { SetupIntroScreen(onStartClick = {}) }
//
//    }
//}

@Composable
fun NavigationRoot() {
    val navController = rememberNavController()

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
            )
    ) {
        NavHost(
            navController = navController,
            startDestination = "splash"
        ) {
            composable("splash") {
                SplashScreen(navController)
            }
            composable("welcome") {
                WelcomeScreen(
                    onStartClick = { navController.navigate("setupInto") }
                )
            }
            composable("setupInto") {
                SetupIntroScreen(onStartClick = {})
            }
        }
    }
}
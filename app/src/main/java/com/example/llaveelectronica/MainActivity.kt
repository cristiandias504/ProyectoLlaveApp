package com.example.llaveelectronica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.llaveelectronica.presentation.navigation.NavigationRoot
import com.example.llaveelectronica.presentation.screens.setupIntoScreen.SetupIntoViewModel
import com.example.llaveelectronica.ui.components.AppBackground
import com.example.llaveelectronica.ui.theme.LlaveElectronicaTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(0),
            navigationBarStyle = SystemBarStyle.dark(0)
        )

        setContent {
            val setupIntoViewModel: SetupIntoViewModel = viewModel()
            val mainActivityViewModel by setupIntoViewModel.setupIntoState

            LlaveElectronicaTheme(
                darkTheme = if (mainActivityViewModel.autoTheme) {
                    isSystemInDarkTheme()
                } else {
                    mainActivityViewModel.selectedThemeDark
                }
            ) {
                AppBackground {
                    NavigationRoot(setupIntoViewModel)
                }
            }
        }
    }
//    override fun onStart() {
//        super.onStart()
//    }
//    override fun onResume() {
//        super.onResume()
//    }
//    override fun onStop() {
//        super.onStop()
//    }
}
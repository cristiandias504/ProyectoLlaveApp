package com.example.llaveelectronica

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.llaveelectronica.data.SetupRepository
import com.example.llaveelectronica.presentation.navigation.NavigationRoot
import com.example.llaveelectronica.presentation.screens.setupIntoScreen.SetupIntoViewModel
import com.example.llaveelectronica.ui.components.AppBackground
import com.example.llaveelectronica.ui.theme.LlaveElectronicaTheme

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(0),
            navigationBarStyle = SystemBarStyle.dark(0)
        )

        setContent {
            val context = LocalContext.current
            val repository = SetupRepository(context)

            val setupIntoViewModel: SetupIntoViewModel = viewModel(
                factory = viewModelFactory {
                    initializer {
                        SetupIntoViewModel(repository)
                    }
                }
            )

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
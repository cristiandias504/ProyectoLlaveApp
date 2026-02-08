package com.example.llaveelectronica.presentation.screens.SplashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SplashScreenViewModel : ViewModel() {
    // Estado que indica si la pantalla debería navegar a la siguiente
    private val _navigateToWelcome = MutableStateFlow(false)
    val navigateToWelcome: StateFlow<Boolean> = _navigateToWelcome.asStateFlow()

    init {
        viewModelScope.launch {
            // Simula delay de splash
            delay(2000L)
            _navigateToWelcome.value = true
        }
    }
}
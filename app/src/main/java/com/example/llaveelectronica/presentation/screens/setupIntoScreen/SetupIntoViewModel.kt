package com.example.llaveelectronica.presentation.screens.setupIntoScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SetupIntoViewModel : ViewModel() {

    private val _setupIntoState = mutableStateOf(SetupIntoDataClass())
    val setupIntoState: State<SetupIntoDataClass> = _setupIntoState

    fun onNextClicked() {
        val nextStep = when (_setupIntoState.value.currentStep) {
            SetupStep.Welcome -> SetupStep.Theme
            SetupStep.Theme -> SetupStep.Permissions
            SetupStep.Permissions -> SetupStep.Pin
            SetupStep.Pin -> SetupStep.PersonalData
            SetupStep.PersonalData -> SetupStep.Vehicle
            SetupStep.Vehicle -> SetupStep.Completed
            SetupStep.Completed -> SetupStep.Theme
        }

        _setupIntoState.value = _setupIntoState.value.copy(
            currentStep = nextStep,
            progress = calculateProgress(nextStep)
        )
    }

    private fun calculateProgress(step: SetupStep): Float {
        return when (step) {
            SetupStep.Welcome -> 0f
            SetupStep.Theme -> 0.2f
            SetupStep.Permissions -> 0.4f
            SetupStep.Pin -> 0.6f
            SetupStep.PersonalData -> 0.8f
            SetupStep.Vehicle -> 1f
            SetupStep.Completed -> 1f
        }
    }

    fun onThemeChange(newAutoTheme: Boolean = false, newTheme: Boolean = true) {
        _setupIntoState.value = _setupIntoState.value.copy(
            autoTheme = newAutoTheme,
            selectedThemeDark = newTheme
        )
    }

    fun requestPermissions(accepted: Boolean) {
        _setupIntoState.value = _setupIntoState.value.copy(
            permissionsGranted = accepted
        )
        if(accepted) {
            onNextClicked()
        }
    }
}
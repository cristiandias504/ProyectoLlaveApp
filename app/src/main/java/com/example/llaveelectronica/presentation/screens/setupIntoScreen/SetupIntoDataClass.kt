package com.example.llaveelectronica.presentation.screens.setupIntoScreen

sealed class SetupStep {
    object Welcome : SetupStep()
    object Theme : SetupStep()
    object Permissions : SetupStep()
    object Pin : SetupStep()
    object PersonalData : SetupStep()
    object Vehicle : SetupStep()
    object Completed : SetupStep()
}

data class SetupIntoDataClass(
    val currentStep: SetupStep = SetupStep.Welcome,

    val progress: Float = 0f,

    val autoTheme: Boolean = false,
    val selectedThemeDark: Boolean = true,

    val permissionsGranted: Boolean = false,
    val pin: String = "",
    val nombre: String = "",
    val apellido: String = "",
    val marca: String = "",
    val modelo: String = "",

    val isSetupCompleted: Boolean = false
)


package com.example.llaveelectronica.presentation.screens.setupIntoScreen

sealed class SetupStep {
    object Welcome : SetupStep()
    object Theme : SetupStep()
    object Permissions : SetupStep()
    object Authentication : SetupStep()
    object PersonalData : SetupStep()
    object Vehicle : SetupStep()
    object Completed : SetupStep()
}

data class SetupIntoDataClass(
    val currentStep: SetupStep = SetupStep.Welcome,

    val progress: Float = 0f,
    val stateButton: Boolean = true,

    val autoTheme: Boolean = false,
    val selectedThemeDark: Boolean = true,

    val permissionsGranted: Boolean = false,
    val permissionsDenied: Boolean = false,

    val pin: String = "",
    val pinConfirmation: String = "",
    val firstPinCompleteEvent: Boolean = false,

    val pinError: Boolean = false,


    val nombre: String = "",
    val apellido: String = "",
    val celular: String = "",
    val datosCompletos: Boolean = false,

    val marca: String = "Seleccionar",
    val modelo: String = "Seleccionar",
    val marcasDisponibles: List<String> = emptyList(),
    val modelosDisponibles: List<String> = emptyList(),

    val isSetupCompleted: Boolean = false
)


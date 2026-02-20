package com.example.llaveelectronica.presentation.screens.setupIntoScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SetupIntoViewModel : ViewModel() {

    private val motorcycle = mapOf(
        "KTM" to listOf("Duke 200 G1", "Duke 200 G2", "Duke 250 G1", "Duke 250 G2", "Duke 390 G1", "Duke 390 G3", "200 Duke", "1290 Super Duke"),
        "Yamaha" to listOf("FZ 250", "MT-03", "R3"),
        "Honda" to listOf("CB190R", "CBR 500R", "XRE 300")
    )

    private val _setupIntoState = mutableStateOf(SetupIntoDataClass(
        marcasDisponibles = motorcycle.keys.toList()
    ))
    val setupIntoState: State<SetupIntoDataClass> = _setupIntoState

    fun onNextClicked() {
        val nextStep = when (_setupIntoState.value.currentStep) {
            SetupStep.Welcome -> SetupStep.Theme
            SetupStep.Theme -> SetupStep.Permissions
            SetupStep.Permissions -> SetupStep.Authentication
            SetupStep.Authentication -> SetupStep.PersonalData
            SetupStep.PersonalData -> SetupStep.Vehicle
            SetupStep.Vehicle -> SetupStep.Completed
            SetupStep.Completed -> SetupStep.Completed
        }

        _setupIntoState.value = _setupIntoState.value.copy(
            currentStep = nextStep,
            progress = calculateProgress(nextStep)
        )
    }

    fun stateButton (state: Boolean) {
        _setupIntoState.value = _setupIntoState.value.copy(
            stateButton = state
        )
    }

    private fun calculateProgress(step: SetupStep): Float {
        return when (step) {
            SetupStep.Welcome -> 0f
            SetupStep.Theme -> 0.2f
            SetupStep.Permissions -> 0.4f
            SetupStep.Authentication -> 0.6f
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
            _setupIntoState.value = _setupIntoState.value.copy(
                permissionsDenied = false
            )
        } else {
            _setupIntoState.value = _setupIntoState.value.copy(
                permissionsDenied = true
            )
        }
    }

    fun registerPin(confirmation: Boolean, digit: String) {
        if (!confirmation){
            if (digit == "D") {
                // Eliminar el ultimo dígito
                if (_setupIntoState.value.pin.isNotEmpty()) {
                    _setupIntoState.value = _setupIntoState.value.copy(
                        pin = _setupIntoState.value.pin.dropLast(1)
                    )
                }
            } else if (digit == "R") {
                // Eliminar totalmente el valor de pin
                _setupIntoState.value = _setupIntoState.value.copy(
                    pin = ""
                )
            } else {
                // Agregar el dígito
                _setupIntoState.value = _setupIntoState.value.copy(
                    pin = _setupIntoState.value.pin + digit,
                    pinError = false
                )
                if (_setupIntoState.value.pin.length == 4) {
                    _setupIntoState.value = _setupIntoState.value.copy(
                        firstPinCompleteEvent = true
                    )
                }
            }
        } else {
            if (digit == "D") {
                // Eliminar el ultimo dígito
                if (_setupIntoState.value.pin.isNotEmpty()) {
                    _setupIntoState.value = _setupIntoState.value.copy(
                        pinConfirmation = _setupIntoState.value.pin.dropLast(1)
                    )
                }
            } else if (digit == "R") {
                // Eliminar totalmente el valor de pin
                _setupIntoState.value = _setupIntoState.value.copy(
                    pinConfirmation = ""
                )
            } else {
                // Agregar el dígito
                _setupIntoState.value = _setupIntoState.value.copy(
                    pinConfirmation = _setupIntoState.value.pinConfirmation + digit
                )
                if (_setupIntoState.value.pinConfirmation.length == 4) {
                    if (_setupIntoState.value.pin == _setupIntoState.value.pinConfirmation) {
                      _setupIntoState.value = _setupIntoState.value.copy(
                          stateButton = true
                      )
                    } else {
                        _setupIntoState.value = _setupIntoState.value.copy(
                            pinError = true,
                            pin = "",
                            pinConfirmation = "",
                            firstPinCompleteEvent = false,
                        )
                    }
                }
            }
        }
    }

    fun registerNombre (nombre: String) {
        _setupIntoState.value = _setupIntoState.value.copy(
            nombre = nombre
        )
        datosCompletos()
    }

    fun registerApellido (apellido: String) {
        _setupIntoState.value = _setupIntoState.value.copy(
            apellido = apellido
        )
        datosCompletos()
    }

    fun registerCelular (celular: String) {
        _setupIntoState.value = _setupIntoState.value.copy(
            celular = celular
        )
        datosCompletos()
    }

    fun datosCompletos () {
        if (_setupIntoState.value.nombre.length >= 2
            && _setupIntoState.value.apellido.length >= 2
            && _setupIntoState.value.celular.length == 10) stateButton(true)
        else stateButton(false)
    }

    fun onMarcaSelected (marca: String) {
        onModeloSelected("Seleccionar")

        val modelos = motorcycle[marca] ?: emptyList()

        _setupIntoState.value = _setupIntoState.value.copy(
            marca = marca,
            modelosDisponibles = modelos,
            stateButton = false
        )
    }

    fun onModeloSelected (modelo: String) {
        _setupIntoState.value = _setupIntoState.value.copy(
            modelo = modelo,
            stateButton = true
        )
    }

    fun setupCompleted (completed: Boolean) {
        _setupIntoState.value = _setupIntoState.value.copy(
            isSetupCompleted = completed
        )
    }
}
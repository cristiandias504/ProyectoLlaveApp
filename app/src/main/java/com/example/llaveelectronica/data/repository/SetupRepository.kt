package com.example.llaveelectronica.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.llaveelectronica.presentation.screens.setupIntoScreen.SetupIntoDataClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SetupRepository(private val context: Context) {

    private val autoTheme = booleanPreferencesKey("auto_theme")
    private val selectedThemeDark = booleanPreferencesKey("selected_theme_dark")
    private val pin = stringPreferencesKey("pin")
    private val authenticationBiometric = booleanPreferencesKey("authentication_biometric")
    private  val nombre = stringPreferencesKey("nombre")
    private  val apellido = stringPreferencesKey("apellido")
    private val celular = stringPreferencesKey("celular")
    private val marca = stringPreferencesKey("marca")
    private val modelo = stringPreferencesKey("modelo")
    private val configuracionInicialCompleta = booleanPreferencesKey("configuracion_inicial_completa")


    suspend fun saveConfig(config: SetupIntoDataClass) {
        context.dataStore.edit { prefs ->
            prefs[autoTheme] = config.autoTheme
            prefs[selectedThemeDark] = config.selectedThemeDark
            prefs[pin] = config.pin
            prefs[authenticationBiometric] = config.isAuthenticationBiometricActive
            prefs[nombre] = config.nombre
            prefs[apellido] = config.apellido
            prefs[celular] = config.celular
            prefs[marca] = config.marca
            prefs[modelo] = config.modelo
            prefs[configuracionInicialCompleta] = config.isSetupCompleted
        }
    }

    val configFlow: Flow<SetupIntoDataClass> =
        context.dataStore.data.map { prefs ->
            SetupIntoDataClass(
                autoTheme = prefs[autoTheme] ?: false,
                selectedThemeDark = prefs[selectedThemeDark] ?: true,
                pin = prefs[pin] ?: "",
                isAuthenticationBiometricActive = prefs[authenticationBiometric] ?: false,
                nombre = prefs[nombre] ?: "",
                apellido = prefs[apellido] ?: "",
                celular = prefs[celular] ?: "",
                marca = prefs[marca] ?: "",
                modelo = prefs[modelo] ?: "",
                isSetupCompleted = prefs[configuracionInicialCompleta] ?: false
            )
        }
}
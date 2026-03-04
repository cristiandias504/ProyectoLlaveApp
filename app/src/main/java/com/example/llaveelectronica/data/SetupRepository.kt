package com.example.llaveelectronica.data

import android.content.Context
import androidx.datastore.preferences.core.*
import com.example.llaveelectronica.presentation.screens.setupIntoScreen.SetupIntoDataClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SetupRepository(private val context: Context) {

    private val AUTO_THEME = booleanPreferencesKey("auto_theme")
    private val SELECTED_THEME_DARK = booleanPreferencesKey("selected_theme_dark")
    private val PIN = stringPreferencesKey("pin")
    private  val NOMBRE = stringPreferencesKey("nombre")
    private  val APELLIDO = stringPreferencesKey("apellido")
    private val CELULAR = stringPreferencesKey("celular")
    private val MARCA = stringPreferencesKey("marca")
    private val MODELO = stringPreferencesKey("modelo")
    private val CONFIGURACION_INICIAL_COMPLETA = booleanPreferencesKey("configuracion_inicial_completa")


    suspend fun saveConfig(config: SetupIntoDataClass) {
        context.dataStore.edit { prefs ->
            prefs[AUTO_THEME] = config.autoTheme
            prefs[SELECTED_THEME_DARK] = config.selectedThemeDark
            prefs[PIN] = config.pin
            prefs[NOMBRE] = config.nombre
            prefs[APELLIDO] = config.apellido
            prefs[CELULAR] = config.celular
            prefs[MARCA] = config.marca
            prefs[MODELO] = config.modelo
            prefs[CONFIGURACION_INICIAL_COMPLETA] = config.isSetupCompleted
        }
    }

    val configFlow: Flow<SetupIntoDataClass> =
        context.dataStore.data.map { prefs ->
            SetupIntoDataClass(
                autoTheme = prefs[AUTO_THEME] ?: false,
                selectedThemeDark = prefs[SELECTED_THEME_DARK] ?: true,
                pin = prefs[PIN] ?: "",
                nombre = prefs[NOMBRE] ?: "",
                apellido = prefs[APELLIDO] ?: "",
                celular = prefs[CELULAR] ?: "",
                marca = prefs[MARCA] ?: "",
                modelo = prefs[MODELO] ?: "",
                isSetupCompleted = prefs[CONFIGURACION_INICIAL_COMPLETA] ?: false
            )
        }
}
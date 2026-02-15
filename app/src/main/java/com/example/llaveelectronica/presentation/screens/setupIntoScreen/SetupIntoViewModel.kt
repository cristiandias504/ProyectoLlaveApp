package com.example.llaveelectronica.presentation.screens.setupIntoScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.lifecycle.ViewModel

class SetupIntoViewModel : ViewModel() {

    private val _progress = mutableFloatStateOf(0f)
    val progress: State<Float> = _progress

    fun setProgress(value: Int) {
        when (value) {
            1 -> _progress.floatValue = 0.1f
            2 -> _progress.floatValue = 0.2f
            3 -> _progress.floatValue = 0.3f
            4 -> _progress.floatValue = 0.4f
        }
    }
}
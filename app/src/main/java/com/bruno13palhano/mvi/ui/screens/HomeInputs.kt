package com.bruno13palhano.mvi.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class HomeInputs {
    var text by mutableStateOf("")
        private set

    fun updateText(text: String) {
        this.text = text
    }

    fun isValid(): Boolean {
        return text.isNotBlank()
    }
}
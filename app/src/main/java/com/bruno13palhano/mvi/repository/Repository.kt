package com.bruno13palhano.mvi.repository

internal interface Repository {
    suspend fun insertText(text: String)
    suspend fun getTexts(): List<String>
}
package com.bruno13palhano.mvi.api

internal interface ApiService {
    suspend fun insertText(text: String)

    suspend fun getTexts1(): List<String>

    suspend fun getTexts2(): List<String>
}
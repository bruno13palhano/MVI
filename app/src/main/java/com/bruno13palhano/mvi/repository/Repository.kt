package com.bruno13palhano.mvi.repository

internal interface Repository {
    suspend fun insertText(text: String)
    suspend fun getTexts(): List<String>
    suspend fun getFastTexts(): List<String>
    suspend fun fakeWork(): Boolean
}
package com.bruno13palhano.mvi.repository

import javax.inject.Inject

internal class FakeRepository @Inject constructor(): Repository {
    private val texts = mutableListOf(
        "Text 1",
        "Text 2",
        "Text 3",
        "Text 4",
        "Text 5",
        "Text 6",
        "Text 7",
        "Text 8"
    )

    override suspend fun insertText(text: String) {
        texts.add(text)
    }

    override suspend fun getTexts(): List<String> {
        return texts
    }
}
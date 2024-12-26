package com.bruno13palhano.mvi.api

import kotlinx.coroutines.delay
import javax.inject.Inject

internal class FakeApi @Inject constructor(): ApiService {
    override suspend fun insertText(text: String) {
        texts1.add(text)
    }

    override suspend fun getTexts1(): List<String> {
        delay(5000)
        return texts1
    }

    override suspend fun getTexts2(): List<String> {
        delay(10000)
        return texts2
    }

    private val texts1 = mutableListOf(
        "Text 1",
        "Text 2",
        "Text 3",
        "Text 4",
        "Text 5",
        "Text 6",
        "Text 7",
        "Text 8"
    )

    private val texts2 = mutableListOf(
        "Text 9",
        "Text 10",
        "Text 11",
        "Text 12",
        "Text 13",
        "Text 14",
        "Text 15",
        "Text 16"
    )
}
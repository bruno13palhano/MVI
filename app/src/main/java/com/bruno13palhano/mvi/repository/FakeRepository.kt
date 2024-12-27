package com.bruno13palhano.mvi.repository

import com.bruno13palhano.mvi.api.ApiService
import com.bruno13palhano.mvi.ui.di.Dispatcher
import com.bruno13palhano.mvi.ui.di.FakeRemoteDatasource
import com.bruno13palhano.mvi.ui.di.MVIDispatchers.DEFAULT
import com.bruno13palhano.mvi.ui.di.MVIDispatchers.IO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class FakeRepository @Inject constructor(
    @FakeRemoteDatasource private val apiService: ApiService,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    @Dispatcher(DEFAULT) private val defaultDispatcher: CoroutineDispatcher
): Repository {
    override suspend fun insertText(text: String) {
        apiService.insertText(text)
    }

    override suspend fun getTexts(): List<String> = withContext(ioDispatcher) {
        val fakeData1 = async { apiService.getTexts1() }

        val fakeData2 = async { apiService.getTexts2() }

        fakeData1.await() + fakeData2.await()
    }

    override suspend fun getFastTexts(): List<String> = coroutineScope {
        select {
            async { apiService.getTexts1() }.onAwait { it }
            async { apiService.getTexts2() }.onAwait { it }
        }.also { coroutineContext.cancelChildren() }
    }

    override suspend fun fakeWork(): Boolean = withContext(defaultDispatcher) {
        delay(5000)
        true
    }
}
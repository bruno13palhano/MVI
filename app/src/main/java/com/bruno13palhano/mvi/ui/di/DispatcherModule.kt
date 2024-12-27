package com.bruno13palhano.mvi.ui.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Qualifier
internal annotation class Dispatcher(val dispatcher: MVIDispatchers)

@Module
@InstallIn(SingletonComponent::class)
internal object DispatcherModule {
    @Provides
    @Dispatcher(MVIDispatchers.IO)
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(MVIDispatchers.MAIN)
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Dispatcher(MVIDispatchers.DEFAULT)
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}

enum class MVIDispatchers {
    IO,
    MAIN,
    DEFAULT
}
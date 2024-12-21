package com.bruno13palhano.mvi.ui.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
internal annotation class IOScope

@Module
@InstallIn(SingletonComponent::class)
internal object ScopeModule {

    @Singleton
    @IOScope
    @Provides
    fun bindIOScope(
        @Dispatcher(MVIDispatchers.IO) dispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)
}
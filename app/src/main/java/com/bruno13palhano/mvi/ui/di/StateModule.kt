package com.bruno13palhano.mvi.ui.di

import com.bruno13palhano.mvi.ui.screens.HomeState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object StateModule {

    @Provides
    @Singleton
    fun provideHomeState(): HomeState = HomeState.initialState
}
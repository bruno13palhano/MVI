package com.bruno13palhano.mvi.ui.di

import com.bruno13palhano.mvi.ui.screens.home.HomeState
import com.bruno13palhano.mvi.ui.screens.other.OtherState
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

    @Provides
    @Singleton
    fun provideOtherState(): OtherState = OtherState.initialState
}
package com.bruno13palhano.mvi.ui.di

import com.bruno13palhano.mvi.repository.Repository
import com.bruno13palhano.mvi.ui.screens.HomePresenter
import com.bruno13palhano.mvi.ui.screens.HomeState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object PresenterModule {
    @Singleton
    @Provides
    fun provideHomePresenter(
        homeInitialState: HomeState,
        repository: Repository,
        @IOScope ioScope: CoroutineScope
    ): HomePresenter = HomePresenter(
        initialState = homeInitialState,
        repository = repository,
        ioScope = ioScope
    )
}
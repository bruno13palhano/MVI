package com.bruno13palhano.mvi.ui.di

import com.bruno13palhano.mvi.ui.screens.HomePresenter
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
internal interface HomePresenterEntryPoint {
    val homePresenter: HomePresenter
}
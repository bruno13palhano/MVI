package com.bruno13palhano.mvi.ui.di

import com.bruno13palhano.mvi.ui.screens.home.HomePresenter
import com.bruno13palhano.mvi.ui.screens.other.OtherPresenter
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
internal interface PresenterEntryPoint {
    val homePresenter: HomePresenter
    val otherPresenter: OtherPresenter
}
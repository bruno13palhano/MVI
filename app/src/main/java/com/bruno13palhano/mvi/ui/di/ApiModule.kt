package com.bruno13palhano.mvi.ui.di

import com.bruno13palhano.mvi.api.ApiService
import com.bruno13palhano.mvi.api.FakeApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

internal annotation class FakeRemoteDatasource

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ApiModule {
    @Binds
    @Singleton
    abstract fun bindApiService(
        fakeApi: FakeApi
    ): ApiService
}
package com.bruno13palhano.mvi.ui.di

import com.bruno13palhano.mvi.repository.FakeRepository
import com.bruno13palhano.mvi.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepository(
        fakeRepository: FakeRepository
    ): Repository
}
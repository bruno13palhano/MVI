package com.bruno13palhano.mvi.ui.screens

import com.bruno13palhano.mvi.BasePresenter
import com.bruno13palhano.mvi.repository.Repository
import com.bruno13palhano.mvi.ui.di.IOScope
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

internal class HomePresenter @Inject constructor(
   initialState: HomeState,
    repository: Repository,
    @IOScope private val ioScope: CoroutineScope
) : BasePresenter<HomeAction, HomeEvent, HomeState, HomeSideEffect>(
    initialState = initialState,
    actionProcessor = HomeActionProcessor(repository = repository),
    reducer = HomeReducer(),
    scope = ioScope
)
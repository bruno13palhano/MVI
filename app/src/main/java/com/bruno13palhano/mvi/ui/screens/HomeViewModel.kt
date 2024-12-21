package com.bruno13palhano.mvi.ui.screens

import com.bruno13palhano.mvi.BaseViewModel
import com.bruno13palhano.mvi.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    homeInitialState: HomeState,
    repository: Repository
): BaseViewModel<HomeAction, HomeEvent, HomeState, HomeSideEffect>(
    initialState = homeInitialState,
    actionProcessor = HomeActionProcessor(repository = repository),
    reducer = HomeReducer()
)
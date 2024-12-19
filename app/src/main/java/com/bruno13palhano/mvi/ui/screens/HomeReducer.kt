package com.bruno13palhano.mvi.ui.screens

import com.bruno13palhano.mvi.Reducer

internal class HomeReducer : Reducer<HomeState, HomeEvent, HomeSideEffect> {
    override fun reduce(
        previousState: HomeState,
        event: HomeEvent
    ): Pair<HomeState, HomeSideEffect?> {
        return when (event) {
            is HomeEvent.Loading -> {
                previousState.copy(isLoading = event.isLoading) to null
            }

            is HomeEvent.Validate -> {
                previousState.copy(
                    isValidating = true,
                    isError = false
                ) to null
            }

            is HomeEvent.UpdateTexts -> {
                previousState.copy(texts = event.texts.toMutableList()) to null
            }

            is HomeEvent.Done -> {
                previousState.copy(
                    isValidating = false,
                ) to HomeSideEffect.NavigateToOtherScreen(text = event.text)
            }

            is HomeEvent.Error -> {
                previousState.copy(
                    isLoading = false,
                    isValidating = false,
                    isError = true
                ) to HomeSideEffect.ShowToast(message = "Fill field")
            }

            is HomeEvent.DismissKeyboard -> {
                previousState to HomeSideEffect.DismissKeyboard
            }
        }
    }
}
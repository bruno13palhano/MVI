package com.bruno13palhano.mvi.ui.screens.home

import androidx.compose.runtime.Immutable

@Immutable
internal data class HomeState(
    val isLoading: Boolean,
    val isError: Boolean,
    val texts: List<String>,
    val homeInputs: HomeInputs,
) {
    companion object {
        val initialState = HomeState(
            isLoading = false,
            isError = false,
            texts = emptyList(),
            homeInputs = HomeInputs()
        )
    }
}

@Immutable
internal sealed class HomeEvent {
    data object UpdateTexts : HomeEvent()
    data object Done : HomeEvent()
    data object DismissKeyboard : HomeEvent()
}

@Immutable
internal sealed class HomeSideEffect {
    data class ShowToast(val message: String) : HomeSideEffect()
    data class NavigateToOtherScreen(val text: String) : HomeSideEffect()
    data object DismissKeyboard : HomeSideEffect()
}
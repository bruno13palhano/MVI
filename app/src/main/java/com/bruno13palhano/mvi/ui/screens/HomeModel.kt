package com.bruno13palhano.mvi.ui.screens

import androidx.compose.runtime.Immutable
import com.bruno13palhano.mvi.ViewAction
import com.bruno13palhano.mvi.ViewEvent
import com.bruno13palhano.mvi.ViewSideEffect
import com.bruno13palhano.mvi.ViewState

@Immutable
internal data class HomeState(
    val isLoading: Boolean,
    val isValidating: Boolean,
    val isError: Boolean,
    val texts: List<String>,
    val homeInputs: HomeInputs,
) : ViewState {
    companion object {
        val initialState = HomeState(
            isLoading = false,
            isValidating = false,
            isError = false,
            texts = emptyList(),
            homeInputs = HomeInputs()
        )
    }
}

@Immutable
internal sealed class HomeEvent : ViewEvent {
    data class Loading(val isLoading: Boolean) : HomeEvent()
    data object Validate : HomeEvent()
    data class UpdateTexts(val texts: List<String>) : HomeEvent()
    data class Done(val text: String) : HomeEvent()
    data object Error : HomeEvent()
    data object DismissKeyboard : HomeEvent()
}

@Immutable
internal sealed class HomeSideEffect : ViewSideEffect {
    data class ShowToast(val message: String) : HomeSideEffect()
    data class NavigateToOtherScreen(val text: String) : HomeSideEffect()
    data object DismissKeyboard : HomeSideEffect()
}

@Immutable
internal sealed class HomeAction : ViewAction {
    data object OnUpdateTexts : HomeAction()
    data object OnValidate : HomeAction()
    data object OnDismissKeyboard : HomeAction()
}
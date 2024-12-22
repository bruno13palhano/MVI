package com.bruno13palhano.mvi.ui.screens.other

import androidx.compose.runtime.Immutable
import com.bruno13palhano.mvi.ViewAction
import com.bruno13palhano.mvi.ViewEvent
import com.bruno13palhano.mvi.ViewSideEffect
import com.bruno13palhano.mvi.ViewState

@Immutable
internal data class OtherState(
    val isLoading: Boolean
) : ViewState {
    companion object {
        val initialState = OtherState(
            isLoading = false
        )
    }
}

@Immutable
internal sealed class OtherEvent : ViewEvent {
    data class Loading(val isLoading: Boolean) : OtherEvent()
    data object NavigateBack : OtherEvent()
}

@Immutable
internal sealed class OtherSideEffect : ViewSideEffect {
    data object NavigateBack : OtherSideEffect()
}

@Immutable
internal sealed class OtherAction : ViewAction {
    data object OnNavigateBack : OtherAction()
}
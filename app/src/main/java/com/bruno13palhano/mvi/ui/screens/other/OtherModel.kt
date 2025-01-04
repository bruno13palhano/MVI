package com.bruno13palhano.mvi.ui.screens.other

import androidx.compose.runtime.Immutable

@Immutable
internal data class OtherState(
    val isLoading: Boolean
) {
    companion object {
        val initialState = OtherState(
            isLoading = false
        )
    }
}

@Immutable
internal sealed class OtherEvent {
    data class Loading(val isLoading: Boolean) : OtherEvent()
    data object WorkDone : OtherEvent()
    data object NavigateBack : OtherEvent()
}

@Immutable
internal sealed class OtherSideEffect {
    data class WorkDone(val message: String) : OtherSideEffect()
    data class WorkProcessing(val message: String) : OtherSideEffect()
    data object NavigateBack : OtherSideEffect()
}
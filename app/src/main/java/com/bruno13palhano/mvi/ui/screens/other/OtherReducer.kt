package com.bruno13palhano.mvi.ui.screens.other

import com.bruno13palhano.mvi.Reducer

internal class OtherReducer : Reducer<OtherState, OtherEvent, OtherSideEffect>{
    override fun reduce(
        previousState: OtherState,
        event: OtherEvent
    ): Pair<OtherState, OtherSideEffect?> {
        return when (event) {
            is OtherEvent.Loading -> {
                previousState.copy(
                    isLoading = event.isLoading
                ) to null
            }

            is OtherEvent.NavigateBack -> {
                previousState to OtherSideEffect.NavigateBack
            }
        }
    }
}
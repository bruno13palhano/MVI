package com.bruno13palhano.mvi.ui.screens.other

import com.bruno13palhano.mvi.ActionProcessor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

internal class OtherActionProcessor : ActionProcessor<OtherAction, OtherState, OtherEvent> {
    override fun process(action: OtherAction, currentState: OtherState): Flow<OtherEvent> {
        return channelFlow {
            when (action) {
                is OtherAction.OnNavigateBack -> {
                    send(OtherEvent.NavigateBack)
                }
            }
        }
    }
}
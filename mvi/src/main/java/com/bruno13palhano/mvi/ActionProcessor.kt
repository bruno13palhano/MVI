package com.bruno13palhano.mvi

import kotlinx.coroutines.flow.Flow

interface ActionProcessor<Action: ViewAction, State: ViewState, Event: ViewEvent> {
    fun process(action: Action, currentState: State): Flow<Event>
}
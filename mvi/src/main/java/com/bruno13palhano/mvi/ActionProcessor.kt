package com.bruno13palhano.mvi

import kotlinx.coroutines.flow.Flow

interface ActionProcessor<Action: ViewAction, Event: ViewEvent> {
    fun process(action: Action): Flow<Event>
}
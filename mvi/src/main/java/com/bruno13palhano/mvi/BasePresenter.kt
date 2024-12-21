package com.bruno13palhano.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BasePresenter<Action: ViewAction, Event: ViewEvent, State: ViewState, SideEffect: ViewSideEffect>(
    initialState: State,
    protected val actionProcessor: ActionProcessor<Action, State, Event>,
    protected val reducer: Reducer<State, Event, SideEffect>,
    protected val scope: CoroutineScope
) {
    private val _effects = Channel<SideEffect>(capacity = Channel.CONFLATED)
    val effects = _effects.receiveAsFlow()

    private val _states: MutableStateFlow<State> = MutableStateFlow(initialState)
    val states: StateFlow<State> = _states

    fun onAction(action: Action) {
        scope.launch {
            actionProcessor.process(
                action = action, currentState = _states
                .value
            ).collect { event ->
                val (newState, sideEffect) =
                    reducer.reduce(previousState = _states.value, event = event)

                _states.value = newState
                sideEffect?.let { effect -> _effects.trySend(effect) }
            }
        }
    }
}
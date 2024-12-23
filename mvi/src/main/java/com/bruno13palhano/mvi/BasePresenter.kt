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
    private val _sideEffect = Channel<SideEffect>(capacity = Channel.CONFLATED)
    val sideEffect = _sideEffect.receiveAsFlow()

    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state

    fun onAction(action: Action) {
        scope.launch {
            actionProcessor.process(
                action = action,
                currentState = _state.value
            ).collect { event ->
                val (newState, sideEffect) =
                    reducer.reduce(previousState = _state.value, event = event)

                _state.value = newState
                sideEffect?.let { effect -> _sideEffect.trySend(effect) }
            }
        }
    }
}
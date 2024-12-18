package com.bruno13palhano.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Action: ViewAction, Event: ViewEvent, State: ViewState, SideEffect: ViewSideEffect>(
    protected val initialState: State,
    protected val actionProcessor: ActionProcessor<Action, Event>,
    protected val reducer: Reducer<State, Event, SideEffect>
) : ViewModel() {
    private val _effects = Channel<SideEffect>(capacity = Channel.CONFLATED)
    val effects = _effects.receiveAsFlow()

    private val _states: MutableStateFlow<State> = MutableStateFlow(initialState)
    val states: StateFlow<State> = _states

    fun onAction(action: Action) {
        viewModelScope.launch {
            actionProcessor.process(action).collect {
                val (newState, sideEffect) = reducer.reduce(previousState = _states.value, event = it)

                _states.value = newState
                sideEffect?.let { effect -> sendEffect(effect) }
            }
        }
    }

    protected fun sendEffect(effect: SideEffect) {
        _effects.trySend(effect)
    }
}
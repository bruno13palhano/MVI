package com.bruno13palhano.mvi.ui.screens

import com.bruno13palhano.mvi.Reducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class HomePresenter(
    private val reducer: Reducer<HomeState, HomeEvent, HomeSideEffect>,
    private val state: HomeState,
    private val events: Flow<HomeEvent>,
    private val sendEvent: (event: HomeEvent) -> Unit,
    private val sendEffect: (effect: HomeSideEffect) -> Unit,
    private val scope: CoroutineScope
) {
    fun presenter(): StateFlow<HomeState> {
        val state = MutableStateFlow(state)

        return flow {
            FlowCollector<HomeState> {
                handleEvents(previousState = state)

                emit(state.value)
            }
        }.stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = state.value
        )
    }

    private suspend fun handleEvents(
        previousState: MutableStateFlow<HomeState>
    ) {
        events.collect { event ->
            val (newState, sideEffect) = reducer.reduce(previousState.value, event)
            previousState.value = newState
            sideEffect?.let { sendEffect(it) }
        }
    }

}
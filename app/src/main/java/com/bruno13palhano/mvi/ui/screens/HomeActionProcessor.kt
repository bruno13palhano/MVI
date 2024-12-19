package com.bruno13palhano.mvi.ui.screens

import com.bruno13palhano.mvi.ActionProcessor
import com.bruno13palhano.mvi.repository.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

internal class HomeActionProcessor(
    private val homeState: HomeState,
    private val repository: Repository
) : ActionProcessor<HomeAction, HomeEvent> {
    override fun process(action: HomeAction): Flow<HomeEvent> {
        return flow {
            when (action) {
                is HomeAction.OnUpdateTexts -> updateTexts()

                is HomeAction.OnValidate -> validate()

                is HomeAction.OnDismissKeyboard -> emit(HomeEvent.DismissKeyboard)
            }
        }
    }

    private suspend fun FlowCollector<HomeEvent>.validate() {
        if (homeState.homeInputs.isValid()) {
            emit(HomeEvent.Validate)
            repository.insertText(text = homeState.homeInputs.text)
            updateTexts()
            emit(HomeEvent.Done(text = homeState.homeInputs.text))
        } else {
            emit(HomeEvent.Error)
        }
    }

    private suspend fun FlowCollector<HomeEvent>.updateTexts() {
        val texts = repository.getTexts()
        emit(HomeEvent.UpdateTexts(texts = texts))
    }
}
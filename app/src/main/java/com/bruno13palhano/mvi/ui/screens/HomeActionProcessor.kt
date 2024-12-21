package com.bruno13palhano.mvi.ui.screens

import com.bruno13palhano.mvi.ActionProcessor
import com.bruno13palhano.mvi.repository.Repository
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

internal class HomeActionProcessor(
    private val repository: Repository
) : ActionProcessor<HomeAction, HomeState, HomeEvent> {
    override fun process(action: HomeAction, currentState: HomeState): Flow<HomeEvent> {
        return channelFlow {
            when (action) {
                is HomeAction.OnUpdateTexts -> updateTexts()

                is HomeAction.OnValidate -> validate(currentState = currentState)

                is HomeAction.OnDismissKeyboard -> send(HomeEvent.DismissKeyboard)
            }

            awaitClose()
        }
    }

    private suspend fun ProducerScope<HomeEvent>.validate(currentState: HomeState) {
        if (currentState.homeInputs.isValid()) {
            done(currentState = currentState)
        } else {
            send(HomeEvent.Error)
        }
    }

    private suspend fun ProducerScope<HomeEvent>.done(currentState: HomeState) {
        repository.insertText(text = currentState.homeInputs.text)
        updateTexts()
        send(HomeEvent.Done(text = currentState.homeInputs.text))
    }

    private suspend fun ProducerScope<HomeEvent>.updateTexts() {
        val texts = repository.getTexts()
        send(HomeEvent.UpdateTexts(texts = texts))
    }
}
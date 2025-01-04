package com.bruno13palhano.mvi.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.mvi.Container
import com.bruno13palhano.mvi.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    val container = Container<HomeState, HomeSideEffect>(
        initialState = HomeState.initialState,
        scope = viewModelScope
    )

    fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.DismissKeyboard -> container.intent {
                postSideEffect(HomeSideEffect.DismissKeyboard)
            }

            HomeEvent.Done -> done()

            HomeEvent.UpdateTexts -> container.intent { updateTexts() }
        }
    }

    private fun done() = container.intent {
        if (state.value.homeInputs.isValid()) {
            repository.insertText(text = state.value.homeInputs.text)

            updateTexts()

            postSideEffect(HomeSideEffect.NavigateToOtherScreen(text = state.value.homeInputs.text))
        } else {
            reduce { copy(isLoading = false, isError = true) }
            postSideEffect(HomeSideEffect.ShowToast(message = "Fill field"))
        }
    }
    
    private suspend fun updateTexts() = container.reduce { 
        val texts = repository.getFastTexts()
        copy(texts = texts)
    }
}
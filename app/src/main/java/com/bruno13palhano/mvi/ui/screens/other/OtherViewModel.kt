package com.bruno13palhano.mvi.ui.screens.other

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.bruno13palhano.mvi.Container
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
internal class OtherViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    val container = Container<OtherState, OtherSideEffect>(
        initialState = OtherState.initialState,
        scope = viewModelScope
    )

    fun handleEvent(event: OtherEvent) {
        when (event) {
            is OtherEvent.Loading -> loading(isLoading = event.isLoading)

            OtherEvent.NavigateBack -> navigateBack()

            is OtherEvent.WorkDone -> workDone()
        }
    }

    private fun loading(isLoading: Boolean) = container.intent {
        reduce { copy(isLoading = isLoading) }
    }

    private fun navigateBack() = container.intent {
        postSideEffect(OtherSideEffect.NavigateBack)
    }

    private fun workDone() = container.intent {
        postSideEffect(OtherSideEffect.WorkProcessing(message = "Processing..."))

        val someWork = SomeWork.startFakeWork()

        WorkManager.getInstance(context = context).apply {
            enqueueUniqueWork(
                "FakeWork",
                ExistingWorkPolicy.REPLACE,
                someWork
            )
        }.getWorkInfoByIdFlow(someWork.id).collect { finished ->
            finished?.state?.isFinished?.let {
                if (it) {
                    postSideEffect(OtherSideEffect.WorkDone(message = "Fake work done..."))
                }
            }
        }
    }
}
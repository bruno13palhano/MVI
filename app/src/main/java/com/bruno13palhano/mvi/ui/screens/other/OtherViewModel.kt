package com.bruno13palhano.mvi.ui.screens.other

import com.bruno13palhano.mvi.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class OtherViewModel @Inject constructor(
    otherInitialState: OtherState
) : BaseViewModel<OtherAction, OtherEvent, OtherState, OtherSideEffect>(
    initialState = otherInitialState,
    actionProcessor = OtherActionProcessor(),
    reducer = OtherReducer(),
)
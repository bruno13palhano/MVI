package com.bruno13palhano.mvi.ui.screens.other

import android.content.Context
import com.bruno13palhano.mvi.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
internal class OtherViewModel @Inject constructor(
    otherInitialState: OtherState,
    @ApplicationContext private val context: Context
) : BaseViewModel<OtherAction, OtherEvent, OtherState, OtherSideEffect>(
    initialState = otherInitialState,
    actionProcessor = OtherActionProcessor(context = context),
    reducer = OtherReducer(),
)
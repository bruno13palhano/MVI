package com.bruno13palhano.mvi.ui.screens.other

import com.bruno13palhano.mvi.BasePresenter
import com.bruno13palhano.mvi.ui.di.IOScope
import kotlinx.coroutines.CoroutineScope

internal class OtherPresenter(
    initialState: OtherState,
    @IOScope private val ioScope: CoroutineScope
) : BasePresenter<OtherAction, OtherEvent, OtherState, OtherSideEffect>(
    initialState = initialState,
    actionProcessor = OtherActionProcessor(),
    reducer = OtherReducer(),
    scope = ioScope,
)
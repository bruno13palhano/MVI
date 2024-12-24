package com.bruno13palhano.mvi.ui.screens.other

import android.content.Context
import com.bruno13palhano.mvi.BasePresenter
import com.bruno13palhano.mvi.ui.di.IOScope
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope

internal class OtherPresenter(
    initialState: OtherState,
    @IOScope private val ioScope: CoroutineScope,
    @ApplicationContext private val context: Context
) : BasePresenter<OtherAction, OtherEvent, OtherState, OtherSideEffect>(
    initialState = initialState,
    actionProcessor = OtherActionProcessor(context = context),
    reducer = OtherReducer(),
    scope = ioScope,
)
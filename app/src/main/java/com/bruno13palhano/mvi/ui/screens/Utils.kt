package com.bruno13palhano.mvi.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.bruno13palhano.mvi.BasePresenter
import com.bruno13palhano.mvi.ui.di.PresenterEntryPoint
import com.bruno13palhano.mvi.ui.screens.home.HomePresenter
import com.bruno13palhano.mvi.ui.screens.other.OtherPresenter
import dagger.hilt.EntryPoints
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> rememberFlowWithLifecycle(
    flow: Flow<T>,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> = remember(flow, lifecycle) {
    flow.flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = minActiveState
    )
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.clickableWithoutRipple(onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = onClick
    )
}

@Composable
internal fun <T : BasePresenter<*, *, *, *>> rememberPresenter(presenter: Class<T>): T {
    val applicationContext = LocalContext.current.applicationContext

    if (presenter.isAssignableFrom(HomePresenter::class.java)) {
        return remember(applicationContext) {
            EntryPoints.get(
                applicationContext,
                PresenterEntryPoint::class.java
            ).homePresenter
        } as T
    } else if (presenter.isAssignableFrom(OtherPresenter::class.java)) {
        return remember(applicationContext) {
            EntryPoints.get(
                applicationContext,
                PresenterEntryPoint::class.java
            ).otherPresenter
        } as T
    } else {
        throw IllegalArgumentException("Presenter not found")
    }
}
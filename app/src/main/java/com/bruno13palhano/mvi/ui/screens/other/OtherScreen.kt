package com.bruno13palhano.mvi.ui.screens.other

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bruno13palhano.mvi.ui.screens.rememberFlowWithLifecycle
import com.bruno13palhano.mvi.ui.screens.rememberPresenter

@Composable
internal fun OtherRoute(
    text: String,
    navigateBack: () -> Unit,
    otherPresenter: OtherPresenter = rememberPresenter(presenter = OtherPresenter::class.java)
) {
    val state by otherPresenter.states.collectAsStateWithLifecycle()
    val effects = rememberFlowWithLifecycle(otherPresenter.effects)

    LaunchedEffect(effects) {
        effects.collect { effect ->
            when (effect) {
                is OtherSideEffect.NavigateBack -> navigateBack()
            }
        }
    }

    HomeContent(
        state = state,
        onAction = otherPresenter::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    state: OtherState,
    onAction: (action: OtherAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Other Screen") },
                navigationIcon = {
                    IconButton(onClick = { onAction(OtherAction.OnNavigateBack) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {

        }
    }
}
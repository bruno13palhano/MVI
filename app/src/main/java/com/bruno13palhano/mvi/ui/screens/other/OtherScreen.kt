package com.bruno13palhano.mvi.ui.screens.other

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bruno13palhano.mvi.ui.screens.rememberFlowWithLifecycle
import kotlinx.coroutines.launch

@Composable
internal fun OtherRoute(
    text: String,
    navigateBack: () -> Unit,
    viewModel: OtherViewModel = hiltViewModel()
) {
    val state by viewModel.container.state.collectAsStateWithLifecycle()
    val sideEffect = rememberFlowWithLifecycle(viewModel.container.sideEffect)

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(sideEffect) {
        sideEffect.collect { effect ->
            when (effect) {
                is OtherSideEffect.WorkDone -> {
                    scope.launch {
                        println("work done: ${effect.message}")
                        snackbarHostState.showSnackbar(
                            message = effect.message,
                            withDismissAction = true
                        )
                    }
                }

                is OtherSideEffect.WorkProcessing -> {
                    scope.launch {
                        println("work processing: ${effect.message}")
                        snackbarHostState.showSnackbar(
                            message = effect.message,
                            withDismissAction = true
                        )
                    }
                }

                is OtherSideEffect.NavigateBack -> navigateBack()
            }
        }
    }

    HomeContent(
        state = state,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::handleEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    state: OtherState,
    snackbarHostState: SnackbarHostState,
    onEvent: (event: OtherEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.consumeWindowInsets(WindowInsets.safeDrawing),
        topBar = {
            TopAppBar(
                title = { Text(text = "Other Screen") },
                navigationIcon = {
                    IconButton(onClick = { onEvent(OtherEvent.NavigateBack) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(OtherEvent.WorkDone) }) {
                Icon(imageVector = Icons.Filled.Build, contentDescription = "Do Work")
            }
        }
    ) {
//        if (state.isLoading) {
//            Box(
//                modifier = Modifier
//                    .padding(it)
//                    .consumeWindowInsets(it)
//                    .fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator(
//                    modifier = Modifier.padding(16.dp),
//                    strokeWidth = 4.dp
//                )
//            }
//        } else {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth()
                    .consumeWindowInsets(it)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(text = "Show Content")
            }
        }
//    }
}
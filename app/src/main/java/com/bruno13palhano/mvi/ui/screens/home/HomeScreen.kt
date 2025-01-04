package com.bruno13palhano.mvi.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bruno13palhano.mvi.ui.screens.clickableWithoutRipple
import com.bruno13palhano.mvi.ui.screens.rememberFlowWithLifecycle
import kotlinx.coroutines.launch

@Composable
internal fun HomeRoute(
    navigateToOtherScreen: (text: String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.container.state.collectAsStateWithLifecycle()
    val sideEffect = rememberFlowWithLifecycle(viewModel.container.sideEffect)

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Only call on first composition
    LaunchedEffect(Unit) {
        if (state.texts.isEmpty()) viewModel.handleEvent(HomeEvent.UpdateTexts)
    }

    LaunchedEffect(sideEffect) {
        sideEffect.collect { effect ->
            when (effect) {
                is HomeSideEffect.ShowToast -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = effect.message,
                            withDismissAction = true
                        )
                    }
                }

                is HomeSideEffect.NavigateToOtherScreen -> navigateToOtherScreen(effect.text)

                is HomeSideEffect.DismissKeyboard -> {
                    focusManager.clearFocus(force = true)
                    keyboardController?.hide()
                }
            }
        }
    }

    HomeContent(
        state = state,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::handleEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun HomeContent(
    snackbarHostState: SnackbarHostState,
    state: HomeState,
    onEvent: (event: HomeEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .consumeWindowInsets(WindowInsets.safeDrawing)
            .clickableWithoutRipple { onEvent(HomeEvent.DismissKeyboard) },
        topBar = {
            TopAppBar(
                title = { Text(text = "MVI") }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(HomeEvent.Done) }) {
                Text(text = "Done")
            }
        }
    ) {
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .consumeWindowInsets(it)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp),
                    strokeWidth = 4.dp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth()
                    .consumeWindowInsets(it)
            ) {
                stickyHeader {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(),
                        value = state.homeInputs.text,
                        onValueChange = state.homeInputs::updateText
                    )
                }

                items(items = state.texts) { text ->
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = text
                    )
                }
            }
        }
    }
}
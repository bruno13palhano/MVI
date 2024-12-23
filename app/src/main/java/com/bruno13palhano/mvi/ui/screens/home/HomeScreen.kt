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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bruno13palhano.mvi.ui.screens.clickableWithoutRipple
import com.bruno13palhano.mvi.ui.screens.rememberFlowWithLifecycle
import com.bruno13palhano.mvi.ui.screens.rememberPresenter
import kotlinx.coroutines.launch

@Composable
internal fun HomeRoute(
    navigateToOtherScreen: (text: String) -> Unit,
    homePresenter: HomePresenter = rememberPresenter(presenter = HomePresenter::class.java)
) {
    val state by homePresenter.state.collectAsStateWithLifecycle()
    val sideEffect = rememberFlowWithLifecycle(homePresenter.sideEffect)

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        homePresenter.onAction(HomeAction.OnUpdateTexts)
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
        onAction = homePresenter::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun HomeContent(
    snackbarHostState: SnackbarHostState,
    state: HomeState,
    onAction: (action: HomeAction) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .consumeWindowInsets(WindowInsets.safeDrawing)
            .clickableWithoutRipple { onAction(HomeAction.OnDismissKeyboard) },
        topBar = {
            TopAppBar(
                title = { Text(text = "MVI") }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAction(HomeAction.OnValidate) }) {
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
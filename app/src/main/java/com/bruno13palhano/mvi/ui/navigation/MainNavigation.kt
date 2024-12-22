package com.bruno13palhano.mvi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bruno13palhano.mvi.ui.screens.HomeRoute
import com.bruno13palhano.mvi.ui.screens.other.OtherRoute
import kotlinx.serialization.Serializable

@Composable
internal fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MainRoutes.Home
    ) {
        composable<MainRoutes.Home> {
            HomeRoute(
                navigateToOtherScreen = { navController.navigate(MainRoutes.Other(it)) }
            )
        }

        composable<MainRoutes.Other> {
            val text = it.toRoute<MainRoutes.Other>().text

            OtherRoute(
                text = text,
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}

internal sealed interface MainRoutes {
    @Serializable
    data object Home : MainRoutes

    @Serializable
    data class Other(val text: String) : MainRoutes
}
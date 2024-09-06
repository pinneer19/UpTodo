package dev.uptodo.app.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import dev.uptodo.app.di.AppComponent
import dev.uptodo.app.navigation.Route.Home
import dev.uptodo.app.navigation.Route.MainGraph
import dev.uptodo.app.ui.screens.home.HomeScreenStateful
import dev.uptodo.app.ui.screens.home.HomeViewModel
import dev.uptodo.app.util.daggerViewModel

fun NavGraphBuilder.mainGraph(
    appComponent: AppComponent,
    navController: NavController,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    navigation<MainGraph>(startDestination = Home) {
        composable<Home> {
            val homeViewModel: HomeViewModel = daggerViewModel {
                appComponent.homeComponent().build().getViewModel()
            }

            HomeScreenStateful(
                viewModel = homeViewModel,
                navController = navController,
                onShowSnackbar = onShowSnackbar
            )
        }
    }
}
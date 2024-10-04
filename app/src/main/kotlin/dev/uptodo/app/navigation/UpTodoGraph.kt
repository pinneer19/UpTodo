package dev.uptodo.app.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.uptodo.app.di.AppComponent
import dev.uptodo.app.ui.screens.splash.SplashScreen
import dev.uptodo.app.ui.util.ObserveAsEvents
import dev.uptodo.app.ui.util.SnackbarController
import dev.uptodo.app.di.util.daggerViewModel
import dev.uptodo.app.util.extension.getSubstringBeforeRouteSymbols
import dev.uptodo.app.util.extension.showBottomBar

@Composable
fun UpTodoGraph(appComponent: AppComponent) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(events = SnackbarController.events) { event ->
        val result = snackbarHostState.showSnackbar(
            message = event.message,
            actionLabel = event.action?.name,
            duration = SnackbarDuration.Short
        )

        if (result == SnackbarResult.ActionPerformed) {
            event.action?.action?.invoke()
        }
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route ?: BottomNavigation.HOME.toString()

    val currentRouteTrimmed by remember(currentRoute) {
        derivedStateOf { currentRoute.getSubstringBeforeRouteSymbols() }
    }

    val showBottomBar by remember(currentRouteTrimmed) {
        derivedStateOf { currentRouteTrimmed.showBottomBar() }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                MainBottomBar(
                    currentRoute = currentRoute,
                    onNavigationBarItemClick = { itemRoute ->
                        if (currentRoute != itemRoute::class.qualifiedName) {
                            navController.navigate(itemRoute) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Route.Splash
        ) {
            composable<Route.Splash> {
                val splashViewModel = daggerViewModel {
                    appComponent.splashComponent().build().getViewModel()
                }

                SplashScreen(viewModel = splashViewModel, navController = navController)
            }

            authGraph(
                appComponent = appComponent,
                navController = navController
            )

            mainGraph(
                appComponent = appComponent,
                navController = navController
            )
        }
    }
}
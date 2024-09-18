package dev.uptodo.app.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
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
import dev.uptodo.app.util.daggerViewModel

@Composable
fun UpTodoGraph(appComponent: AppComponent) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val onShowSnackbar: suspend (String, String?) -> Boolean = { message, action ->
        snackbarHostState.showSnackbar(
            message = message,
            actionLabel = action,
            duration = SnackbarDuration.Short,
        ) == SnackbarResult.ActionPerformed
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route ?: BottomNavigation.HOME.toString()

    val currentRouteTrimmed by remember(currentRoute) {
        derivedStateOf { currentRoute.substringBefore("/") }
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
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    BottomNavigation.entries.forEach { navigationItem ->
                        val isSelected by remember(currentRoute) {
                            derivedStateOf { currentRoute == navigationItem.route::class.qualifiedName }
                        }

                        NavigationBarItem(
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                            ),
                            selected = isSelected,
                            label = { Text(navigationItem.label) },
                            onClick = {
                                if (currentRoute != navigationItem.route::class.qualifiedName) {
                                    navController.navigate(navigationItem.route) {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = navigationItem.icon,
                                    contentDescription = navigationItem.label
                                )
                            }
                        )
                    }
                }
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
                navController = navController,
                onShowSnackbar = onShowSnackbar
            )

            mainGraph(
                appComponent = appComponent,
                navController = navController,
                onShowSnackbar = onShowSnackbar
            )
        }
    }
}
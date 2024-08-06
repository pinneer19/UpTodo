package dev.uptodo.app.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import dev.uptodo.app.di.AppComponent
import dev.uptodo.app.model.LocalAuthState

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

    val currentRoute = navBackStackEntry?.destination?.route
        ?: BottomNavigation.HOME::class.qualifiedName.orEmpty()

    val currentRouteTrimmed by remember(currentRoute) {
        derivedStateOf { currentRoute.substringBefore("?") }
    }

    val showBottomBar by remember(currentRouteTrimmed) {
        derivedStateOf { !currentRouteTrimmed.isAuthRoute() }
    }

    val authState = LocalAuthState.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            if(showBottomBar) {
                NavigationBar {
                    BottomNavigation.entries.forEach { navigationItem ->
                        val isSelected by remember(currentRoute) {
                            derivedStateOf { currentRoute == navigationItem.route::class.qualifiedName }
                        }

                        NavigationBarItem(
                            selected = isSelected,
                            label = { Text(navigationItem.label) },
                            onClick = { navController.navigate(navigationItem.route) },
                            icon = { navigationItem.icon }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        println(authState.isAuthenticated)
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = if (authState.isAuthenticated) Route.MainGraph else Route.AuthGraph
        ) {
            authGraph(
                appComponent = appComponent,
                navController = navController,
                onShowSnackbar = onShowSnackbar
            )

            mainGraph(appComponent = appComponent)
        }
    }
}
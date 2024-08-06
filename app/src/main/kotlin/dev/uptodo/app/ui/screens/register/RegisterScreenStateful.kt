package dev.uptodo.app.ui.screens.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import dev.uptodo.app.navigation.Route

@Composable
fun RegisterScreenStateful(
    viewModel: RegisterViewModel,
    navController: NavController,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    val uiState by viewModel.uiState.collectAsState()

    RegisterScreen(
        uiState = uiState,
        onShowSnackbar = onShowSnackbar,
        onEvent = viewModel::onEvent,
        onNavigateToMainScreen = { navController.navigate(Route.MainGraph) },
        onNavigateToLogin = { navController.navigateUp() },
    )
}
package dev.uptodo.app.ui.screens.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import dev.uptodo.app.navigation.Route
import dev.uptodo.app.ui.screens.register.viewmodel.RegisterViewModel

@Composable
fun RegisterScreenStateful(
    viewModel: RegisterViewModel,
    navController: NavController,
) {
    val uiState by viewModel.uiState.collectAsState()

    RegisterScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onNavigateToMainScreen = { navController.navigate(Route.MainGraph) },
        onNavigateToLogin = { navController.navigateUp() },
    )
}
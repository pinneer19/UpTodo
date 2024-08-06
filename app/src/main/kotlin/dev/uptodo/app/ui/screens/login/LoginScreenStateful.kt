package dev.uptodo.app.ui.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import dev.uptodo.app.navigation.Route

@Composable
fun LoginScreenStateful(
    viewModel: LoginViewModel,
    navController: NavController,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    val uiState by viewModel.uiState.collectAsState()

    LoginScreen(
        uiState = uiState,
        onShowSnackbar = onShowSnackbar,
        onNavigateToRegister = { navController.navigate(Route.Register) },
        onNavigateToMainScreen = { navController.navigate(Route.MainGraph) },
        onNavigateToPasswordResetScreen = { navController.navigate(Route.PasswordReset) },
        onEvent = viewModel::onEvent
    )
}
package dev.uptodo.app.ui.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import dev.uptodo.app.navigation.AuthRoute
import dev.uptodo.app.navigation.Route
import dev.uptodo.app.ui.screens.login.viewmodel.LoginViewModel

@Composable
fun LoginScreenStateful(
    viewModel: LoginViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()

    LoginScreen(
        uiState = uiState,
        onNavigateToRegister = { navController.navigate(AuthRoute.Register) },
        onNavigateToMainScreen = { navController.navigate(Route.MainGraph) },
        onNavigateToPasswordResetScreen = { navController.navigate(AuthRoute.PasswordReset) },
        onEvent = viewModel::onEvent
    )
}
package dev.uptodo.app.ui.screens.passwordReset

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController

@Composable
fun PasswordResetScreenStateful(viewModel: PasswordResetViewModel, navController: NavController) {

    val uiState by viewModel.uiState.collectAsState()

    PasswordResetScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onNavigateUp = { navController.navigateUp() }
    )
}
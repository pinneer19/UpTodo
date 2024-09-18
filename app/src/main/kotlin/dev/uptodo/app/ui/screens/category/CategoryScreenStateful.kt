package dev.uptodo.app.ui.screens.category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController

@Composable
fun CategoryScreenStateful(
    viewModel: CategoryViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()

    CategoryScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onNavigateUp = { navController.navigateUp() }
    )
}
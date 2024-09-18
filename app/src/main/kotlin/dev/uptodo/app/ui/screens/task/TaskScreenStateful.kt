package dev.uptodo.app.ui.screens.task

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import dev.uptodo.app.navigation.Route

@Composable
fun TaskScreenStateful(
    viewModel: TaskViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()

    TaskScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onNavigateUp = { navController.navigateUp() },
        onNavigateToCategoryScreen = { navController.navigate(Route.Category) }
    )
}
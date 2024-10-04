package dev.uptodo.app.ui.screens.task

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import dev.uptodo.app.navigation.MainRoute
import dev.uptodo.app.ui.screens.task.viewmodel.TaskViewModel

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
        onNavigateToCategoryScreen = { navController.navigate(MainRoute.Category) }
    )
}
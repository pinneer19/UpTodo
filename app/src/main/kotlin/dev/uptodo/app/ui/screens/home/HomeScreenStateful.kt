package dev.uptodo.app.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import dev.uptodo.app.navigation.MainRoute
import dev.uptodo.app.ui.screens.home.viewmodel.HomeViewModel

@Composable
fun HomeScreenStateful(
    viewModel: HomeViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onNavigateToCategoryScreen = { categoryId, category ->
            navController.navigate(
                MainRoute.Category(
                    categoryId,
                    category
                )
            )
        },
        onNavigateToTaskDetails = { id, task ->
            navController.navigate(
                MainRoute.TaskDetails(id, task)
            )
        }
    )
}
package dev.uptodo.app.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import dev.uptodo.app.navigation.Route

@Composable
fun HomeScreenStateful(
    viewModel: HomeViewModel,
    navController: NavController,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {

    val uiState by viewModel.uiState.collectAsState()

    HomeScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onNavigateToCategoryScreen = { navController.navigate(Route.AddTaskCategory) }
    )
}
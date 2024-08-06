package dev.uptodo.app.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun HomeScreenStateful(viewModel: HomeViewModel) {

    val uiState by viewModel.uiState.collectAsState()

    HomeScreen(tasks = uiState.tasks)
}
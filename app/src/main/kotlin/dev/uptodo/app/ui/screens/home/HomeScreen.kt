package dev.uptodo.app.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.uptodo.app.ui.components.TopBarComponent
import dev.uptodo.app.ui.theme.UpTodoTheme
import dev.uptodo.domain.model.Task

@Composable
fun HomeScreen(
    uiState: HomeState,
    onEvent: (HomeEvent) -> Unit,
    onNavigateToCategoryScreen: () -> Unit,
    onNavigateToTaskDetails: (String, Task) -> Unit,
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = { showBottomSheet = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        topBar = {
            TopBarComponent(
                title = "Index",
                leadingIcon = Icons.Default.FilterList,
                leadingAction = {}
            )
        }
    ) { innerPadding ->
        val showListPlaceholder =
            uiState.todoTasks.isEmpty() && uiState.completedTasks.isEmpty()

        AnimatedVisibility(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            visible = showListPlaceholder,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            TaskListPlaceholder()
        }

        AnimatedVisibility(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            visible = !showListPlaceholder,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            TaskList(
                todoTasks = uiState.todoTasks,
                completedTasks = uiState.completedTasks,
                onEvent = onEvent,
                modifier = Modifier.fillMaxWidth(),
                onNavigateToTaskDetails = onNavigateToTaskDetails
            )
        }

        if (showBottomSheet) {
            AddTaskModalBottomSheet(
                onEvent = onEvent,
                title = uiState.sheetTaskTitle,
                priority = uiState.sheetTaskPriority.toInt(),
                categoryId = uiState.sheetTaskCategoryId,
                description = uiState.sheetTaskDescription,
                onDismissRequest = { showBottomSheet = !showBottomSheet },
                categories = uiState.categories,
                onAddTaskCategory = {
                    showBottomSheet = false
                    onNavigateToCategoryScreen()
                }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    UpTodoTheme {
        HomeScreen(
            uiState = HomeState(),
            onEvent = {},
            onNavigateToCategoryScreen = {},
            onNavigateToTaskDetails = { _, _ -> }
        )
    }
}
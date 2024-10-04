package dev.uptodo.app.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.uptodo.app.R
import dev.uptodo.app.UpTodoApp
import dev.uptodo.app.di.util.daggerViewModel
import dev.uptodo.app.ui.components.TopBarComponent
import dev.uptodo.app.ui.screens.home.bottomsheet.TaskModalBottomSheet
import dev.uptodo.app.ui.screens.home.tasklist.TaskList
import dev.uptodo.app.ui.screens.home.tasklist.TaskListPlaceholder
import dev.uptodo.app.ui.screens.home.viewmodel.HomeEvent
import dev.uptodo.app.ui.screens.home.viewmodel.HomeState
import dev.uptodo.app.ui.theme.UpTodoTheme
import dev.uptodo.app.ui.util.SnackbarController
import dev.uptodo.app.ui.util.toUiText
import dev.uptodo.app.util.helper.CheckNotificationPermission
import dev.uptodo.domain.model.Task
import dev.uptodo.domain.model.TaskCategory

@Composable
fun HomeScreen(
    uiState: HomeState,
    onEvent: (HomeEvent) -> Unit,
    onNavigateToCategoryScreen: (categoryId: String?, category: TaskCategory?) -> Unit,
    onNavigateToTaskDetails: (String, Task) -> Unit,
) {
    val error = uiState.error
    val context = LocalContext.current

    LaunchedEffect(error) {
        error?.let {
            SnackbarController.sendMessageEvent(
                message = it.asString(context)
            )
            onEvent(HomeEvent.ClearError)
        }
    }

    CheckNotificationPermission(
        onPermissionNotGranted = {
            onEvent(
                HomeEvent.UpdateError(
                    message = R.string.enable_notification_permission.toUiText()
                )
            )
        }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(HomeEvent.ShowBottomSheet) }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        topBar = {
            TopBarComponent(
                title = stringResource(R.string.index),
                modifier = Modifier.padding(bottom = 20.dp)
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

        if (uiState.showBottomSheet) {
            val taskSheetViewModel = daggerViewModel {
                (context.applicationContext as UpTodoApp).getAppComponent()
                    .homeComponent()
                    .build()
                    .getTaskSheetViewModel()
            }

            val taskSheetUiState by taskSheetViewModel.uiState.collectAsState()

            TaskModalBottomSheet(
                uiState = taskSheetUiState,
                onEvent = taskSheetViewModel::onEvent,
                onHideBottomSheet = { onEvent(HomeEvent.HideBottomSheet) },
                onNavigateToCategoryScreen = onNavigateToCategoryScreen,
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
            onNavigateToCategoryScreen = { _, _ -> },
            onNavigateToTaskDetails = { _, _ -> },
        )
    }
}
package dev.uptodo.app.ui.screens.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.uptodo.app.R
import dev.uptodo.app.ui.components.TopBarComponent
import dev.uptodo.app.ui.screens.task.component.TaskInfo
import dev.uptodo.app.ui.screens.task.fab.ExpandableFab
import dev.uptodo.app.ui.screens.task.fab.FabAction
import dev.uptodo.app.ui.screens.task.property.CategoryProperty
import dev.uptodo.app.ui.screens.task.property.DateTimeProperty
import dev.uptodo.app.ui.screens.task.property.PriorityProperty
import dev.uptodo.app.ui.screens.task.property.SubtaskProperty
import dev.uptodo.app.ui.screens.task.viewmodel.TaskEvent
import dev.uptodo.app.ui.screens.task.viewmodel.TaskState
import dev.uptodo.app.ui.theme.UpTodoTheme
import dev.uptodo.domain.model.TaskPriority
import kotlinx.datetime.LocalDateTime

@Composable
fun TaskScreen(
    uiState: TaskState,
    onEvent: (TaskEvent) -> Unit,
    onNavigateUp: () -> Unit,
    onNavigateToCategoryScreen: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarComponent(
                title = stringResource(R.string.task_details),
                leadingIcon = Icons.Default.Close,
                leadingAction = onNavigateUp
            )
        },
        floatingActionButton = {
            ExpandableFab(
                actions = listOf(
                    FabAction(
                        name = stringResource(R.string.delete_task),
                        icon = Icons.Default.Delete,
                        onAction = {
                            onEvent(TaskEvent.DeleteTask)
                            onNavigateUp()
                        }
                    ),
                    FabAction(
                        name = stringResource(R.string.save_task),
                        icon = Icons.Default.Save,
                        onAction = {
                            onEvent(TaskEvent.EditTask)
                            onNavigateUp()
                        }
                    )
                )
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .consumeWindowInsets(contentPadding)
                .imePadding()
                .padding(start = 24.dp, end = 24.dp, top = 15.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 30.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            TaskInfo(
                name = uiState.name,
                description = uiState.description,
                modifier = Modifier.fillMaxWidth(),
                onUpdateTaskName = { onEvent(TaskEvent.UpdateTaskName(it)) },
                onUpdateTaskDescription = { onEvent(TaskEvent.UpdateTaskDescription(it)) }
            )

            DateTimeProperty(
                deadline = uiState.deadline,
                onEvent = onEvent
            )

            CategoryProperty(
                categoryId = uiState.categoryId,
                availableCategories = uiState.categories,
                onSaveCategory = { selectedCategoryId ->
                    onEvent(TaskEvent.UpdateTaskCategory(selectedCategoryId))
                },
                onAddCategory = onNavigateToCategoryScreen
            )

            PriorityProperty(
                priority = uiState.priority,
                onEvent = onEvent
            )

            SubtaskProperty(
                subtasks = uiState.subtasks,
                onUpdateSubtask = { index, subtaskName ->
                    onEvent(
                        TaskEvent.UpdateSubtask(
                            index,
                            subtaskName
                        )
                    )
                },
                onAddSubtask = { onEvent(TaskEvent.AddSubtask) },
                onRemoveSubtask = { index -> onEvent(TaskEvent.RemoveSubtask(index)) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskScreenPreview() {
    UpTodoTheme {
        TaskScreen(
            onEvent = {},
            onNavigateUp = {},
            onNavigateToCategoryScreen = {},
            uiState = TaskState(
                name = "Pizzza",
                description = "Buy pizza",
                deadline = LocalDateTime(2024, 11, 11, 11, 11),
                subtasks = emptyList(),
                priority = TaskPriority.DEFAULT
            )
        )
    }
}
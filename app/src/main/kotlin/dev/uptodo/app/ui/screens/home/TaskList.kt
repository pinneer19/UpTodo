package dev.uptodo.app.ui.screens.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.uptodo.domain.model.Task
import dev.uptodo.domain.model.TaskCategory
import dev.uptodo.domain.model.TaskPriority
import dev.uptodo.ui.theme.UpTodoTheme
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun TaskList(tasks: List<Task>, modifier: Modifier = Modifier) {

    LazyColumn {
        items(tasks) {
            TaskView(it)
        }
    }
}

@Composable
fun TaskView(task: Task, modifier: Modifier = Modifier) {
    Row {
    }
}

@Preview
@Composable
fun TaskPreview() {
    UpTodoTheme {
        TaskView(
            task = Task(
                name = "Do math",
                description = "",
                category = TaskCategory("Uni", "", ""),
                priority = TaskPriority.ONE,
                subtasks = emptyList(),
                deadline = Clock.System.now().toLocalDateTime(TimeZone.UTC)
            )
        )
    }
}
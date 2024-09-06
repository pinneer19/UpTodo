package dev.uptodo.app.ui.screens.home

import dev.uptodo.domain.model.Task
import dev.uptodo.domain.model.TaskCategory
import dev.uptodo.domain.model.TaskPriority
import kotlinx.datetime.LocalDateTime

data class HomeState(
    val todoTasks: List<Pair<String, Task>> = emptyList(),
    val completedTasks: List<Pair<String, Task>> = emptyList(),
    val categories: Map<String, TaskCategory> = emptyMap(),
    val sheetTaskTitle: String = "",
    val sheetTaskDescription: String = "",
    val sheetTaskDeadline: LocalDateTime? = null,
    val sheetTaskPriority: TaskPriority = TaskPriority.DEFAULT,
    val sheetTaskCategoryId: String = "",
    val error: String? = null
)
package dev.uptodo.app.ui.screens.task

import dev.uptodo.domain.model.Subtask
import dev.uptodo.domain.model.TaskCategory
import dev.uptodo.domain.model.TaskPriority
import kotlinx.datetime.LocalDateTime

data class TaskState(
    val name: String,
    val description: String = "",
    val deadline: LocalDateTime? = null,
    val priority: TaskPriority = TaskPriority.DEFAULT,
    val categoryId: String? = null,
    val subtasks: List<Subtask> = emptyList(),
    val categories: Map<String, TaskCategory> = mapOf(),
    val error: String? = null
)
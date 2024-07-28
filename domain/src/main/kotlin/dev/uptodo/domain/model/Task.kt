package dev.uptodo.domain.model

import kotlinx.datetime.LocalDateTime

data class Task(
    val name: String,
    val description: String,
    val priority: TaskPriority,
    val category: TaskCategory,
    val subtasks: List<Subtask>,
    val deadline: LocalDateTime,
)
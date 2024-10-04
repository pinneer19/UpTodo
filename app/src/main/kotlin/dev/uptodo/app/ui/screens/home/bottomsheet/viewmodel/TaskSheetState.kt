package dev.uptodo.app.ui.screens.home.bottomsheet.viewmodel

import dev.uptodo.app.ui.util.UiText
import dev.uptodo.domain.model.TaskCategory
import dev.uptodo.domain.model.TaskPriority
import kotlinx.datetime.LocalDateTime

data class TaskSheetState(
    val title: String = "",
    val description: String = "",
    val deadline: LocalDateTime? = null,
    val priority: TaskPriority = TaskPriority.DEFAULT,
    val categoryId: String? = null,
    val categories: Map<String, TaskCategory> = emptyMap(),
    val speaking: Boolean = false,
    val error: UiText? = null
)
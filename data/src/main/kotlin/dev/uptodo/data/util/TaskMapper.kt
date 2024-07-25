package dev.uptodo.data.util

import dev.uptodo.data.model.TaskDto
import dev.uptodo.domain.model.Task

internal fun Task.toTaskDto(id: String, categoryId: String) = TaskDto(
    id, name, description, priority, categoryId, subtasks, deadline
)
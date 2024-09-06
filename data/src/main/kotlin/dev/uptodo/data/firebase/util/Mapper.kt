package dev.uptodo.data.firebase.util

import dev.uptodo.data.firebase.model.TaskDto
import dev.uptodo.domain.model.Task

internal fun Task.toTaskDto(id: String, categoryId: String?) = TaskDto(
    id, name, description, priority, categoryId, subtasks, deadline,
)
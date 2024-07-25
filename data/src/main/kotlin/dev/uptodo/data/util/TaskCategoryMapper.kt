package dev.uptodo.data.util

import dev.uptodo.data.model.TaskCategoryDto
import dev.uptodo.domain.model.TaskCategory

internal fun TaskCategory.toTaskCategoryDto(id: String) = TaskCategoryDto(
    id, name, iconUri, iconTint
)
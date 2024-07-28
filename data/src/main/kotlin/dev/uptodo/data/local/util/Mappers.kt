package dev.uptodo.data.local.util

import dev.uptodo.data.local.entity.TaskCategoryEntity
import dev.uptodo.data.local.entity.TaskEntity
import dev.uptodo.domain.model.Task
import dev.uptodo.domain.model.TaskCategory

internal fun Task.toTaskEntity(id: String, categoryId: String) = TaskEntity(
    id, name, description, priority, categoryId, subtasks, deadline
)

internal fun TaskCategoryEntity.toTaskCategory() = TaskCategory(
    name, iconUri, iconTint
)

internal fun TaskCategory.toTaskCategoryEntity(id: String) = TaskCategoryEntity(
    id, name, iconUri, iconTint
)
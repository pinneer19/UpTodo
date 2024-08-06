package dev.uptodo.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation
import dev.uptodo.data.local.util.toTaskCategory
import dev.uptodo.domain.model.Task

data class TaskWithCategory(
    @Embedded val task: TaskEntity,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val category: TaskCategoryEntity
) {
    fun toTask() = Task(
        name = task.name,
        description = task.description,
        priority = task.priority,
        category = category.toTaskCategory(),
        subtasks = task.subtasks,
        deadline = task.deadline
    )
}
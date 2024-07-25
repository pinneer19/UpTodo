package dev.uptodo.data.model

import androidx.room.Embedded
import androidx.room.Relation
import dev.uptodo.domain.model.Task


internal data class TaskWithCategory(
    @Embedded val task: TaskDto,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: TaskCategoryDto
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
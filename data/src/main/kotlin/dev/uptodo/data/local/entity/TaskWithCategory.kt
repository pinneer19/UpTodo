package dev.uptodo.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation
import dev.uptodo.data.local.util.toTaskCategory
import dev.uptodo.domain.model.Task

data class  TaskWithCategory(
    @Embedded val task: TaskEntity,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val category: TaskCategoryEntity?
) {
    fun toTask() = with(task) {
        Task(
            name = name,
            description = description,
            priority = priority,
            category = categoryId?.let { category?.toTaskCategory() },
            subtasks = subtasks,
            deadline = deadline,
            completed = completed
        )
    }
}
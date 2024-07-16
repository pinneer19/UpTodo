package dev.uptodo.domain.repository

import dev.uptodo.domain.model.Subtask
import dev.uptodo.domain.model.Task
import dev.uptodo.domain.model.TaskCategory
import dev.uptodo.domain.model.TaskPriority
import kotlinx.datetime.LocalDateTime

interface TaskRepository {
    suspend fun createTaskCategory(
        categoryName: String,
        iconUri: String,
        iconTint: String
    ): Result<Unit>

    suspend fun createTask(
        name: String,
        description: String,
        priority: TaskPriority,
        category: TaskCategory,
        subtasks: List<Subtask>,
        deadline: LocalDateTime
    ): Result<Unit>

    suspend fun deleteTaskCategory(categoryId: Int): Result<Unit>

    suspend fun deleteTask(taskId: Int): Result<Unit>

    suspend fun updateTaskCategory(taskCategory: TaskCategory): Result<Unit>

    suspend fun updateTask(task: Task): Result<Unit>

    suspend fun initializeTaskCategories(): Result<Unit>

    suspend fun synchronizeData(): Result<Unit>
}
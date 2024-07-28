package dev.uptodo.domain.repository

import dev.uptodo.domain.model.TaskCategory

interface TaskCategoryRepository {
    suspend fun getTaskCategories(): Result<Map<String, TaskCategory>>

    suspend fun createTaskCategory(
        categoryName: String,
        iconUri: String,
        iconTint: String
    ): Result<String>

    suspend fun deleteTaskCategory(id: String): Result<Unit>

    suspend fun updateTaskCategory(id: String, taskCategory: TaskCategory): Result<Unit>
}

interface OfflineTaskCategoryRepository : TaskCategoryRepository {
    suspend fun initializeTaskCategories(taskCategories: List<TaskCategory>): Result<Unit>
}

interface RemoteTaskCategoryRepository : TaskCategoryRepository
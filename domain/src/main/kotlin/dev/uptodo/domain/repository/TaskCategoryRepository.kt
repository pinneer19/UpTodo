package dev.uptodo.domain.repository

import dev.uptodo.domain.model.TaskCategory
import kotlinx.coroutines.flow.Flow

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

    suspend fun getTaskCategoryIdByName(categoryName: String): Result<String>

    fun getIconNames(): List<String>

    fun getTaskCategoriesFlow(): Flow<Map<String, TaskCategory>>
}

interface RemoteTaskCategoryRepository : TaskCategoryRepository
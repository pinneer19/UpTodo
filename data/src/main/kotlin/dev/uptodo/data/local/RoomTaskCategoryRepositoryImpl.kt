package dev.uptodo.data.local

import dev.uptodo.data.model.TaskCategoryDto
import dev.uptodo.data.util.getResult
import dev.uptodo.data.util.toTaskCategoryDto
import dev.uptodo.domain.model.TaskCategory
import dev.uptodo.domain.repository.TaskCategoryRepository
import java.util.UUID
import javax.inject.Inject

internal class RoomTaskCategoryRepositoryImpl @Inject constructor(
    private val taskCategoryDao: TaskCategoryDao
) : TaskCategoryRepository {
    override suspend fun getTaskCategories(): Result<Map<String, TaskCategory>> {
        return getResult<Map<String, TaskCategory>> {
            taskCategoryDao.getAllTaskCategories().associate { dto ->
                dto.id to dto.toTaskCategory()
            }
        }
    }

    override suspend fun createTaskCategory(
        categoryName: String,
        iconUri: String,
        iconTint: String
    ): Result<String> {
        return getResult<String> {
            val taskCategoryId = UUID.randomUUID().toString()

            taskCategoryDao.createTaskCategory(
                TaskCategoryDto(taskCategoryId, categoryName, iconUri, iconTint)
            )

            taskCategoryId
        }
    }

    override suspend fun deleteTaskCategory(id: String): Result<Unit> {
        return getResult {
            taskCategoryDao.deleteTaskCategoryById(id)
        }
    }

    override suspend fun updateTaskCategory(id: String, taskCategory: TaskCategory): Result<Unit> {
        return getResult {
            taskCategoryDao.updateTaskCategory(taskCategory.toTaskCategoryDto(id))
        }
    }
}
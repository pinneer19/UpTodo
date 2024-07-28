package dev.uptodo.data.repository

import dev.uptodo.data.local.dao.TaskCategoryDao
import dev.uptodo.data.local.entity.TaskCategoryEntity
import dev.uptodo.data.local.util.toTaskCategory
import dev.uptodo.data.local.util.toTaskCategoryEntity
import dev.uptodo.data.util.getResult
import dev.uptodo.domain.model.TaskCategory
import dev.uptodo.domain.repository.OfflineTaskCategoryRepository
import java.util.UUID
import javax.inject.Inject

internal class OfflineTaskCategoryRepositoryImpl @Inject constructor(
    private val taskCategoryDao: TaskCategoryDao
) : OfflineTaskCategoryRepository {

    override suspend fun initializeTaskCategories(taskCategories: List<TaskCategory>): Result<Unit> {
        return getResult {
            taskCategoryDao.initializeTaskCategories(
                taskCategories.map {
                    val id = UUID.randomUUID().toString()
                    it.toTaskCategoryEntity(id)
                }
            )
        }
    }

    override suspend fun getTaskCategories(): Result<Map<String, TaskCategory>> {
        return getResult<Map<String, TaskCategory>> {
            taskCategoryDao.getAllTaskCategories().associate { entity ->
                entity.id to entity.toTaskCategory()
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
                TaskCategoryEntity(taskCategoryId, categoryName, iconUri, iconTint)
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
            taskCategoryDao.updateTaskCategory(taskCategory.toTaskCategoryEntity(id))
        }
    }
}
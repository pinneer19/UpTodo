package dev.uptodo.data.repository

import android.content.Context
import dev.uptodo.data.local.dao.TaskCategoryDao
import dev.uptodo.data.local.entity.TaskCategoryEntity
import dev.uptodo.data.local.util.toTaskCategory
import dev.uptodo.data.local.util.toTaskCategoryEntity
import dev.uptodo.data.util.getResult
import dev.uptodo.domain.model.TaskCategory
import dev.uptodo.domain.repository.OfflineTaskCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.UUID
import javax.inject.Inject

class OfflineTaskCategoryRepositoryImpl @Inject constructor(
    private val taskCategoryDao: TaskCategoryDao,
    private val context: Context
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

    override suspend fun getTaskCategoryIdByName(categoryName: String): Result<String> {
        return getResult {
            taskCategoryDao.getTaskCategoryId(categoryName)
        }
    }

    override fun getIconNames(): List<String> {
        val inputStream = context.assets.open("icons_names.txt")

        return BufferedReader(InputStreamReader(inputStream)).use {
            it.readLines()
        }
    }

    override suspend fun getTaskCategories(): Result<Map<String, TaskCategory>> {
        return getResult {
            taskCategoryDao.getAllTaskCategories().associate { entity ->
                entity.id to entity.toTaskCategory()
            }
        }
    }

    override fun getTaskCategoriesFlow(): Flow<Map<String, TaskCategory>> {
        return taskCategoryDao.getTaskCategoriesFlow().map { entityList ->
            entityList.associate { entity ->
                entity.id to entity.toTaskCategory()
            }
        }
    }

    override suspend fun createTaskCategory(
        categoryName: String,
        iconUri: String,
        iconTint: String
    ): Result<String> {
        return getResult {
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
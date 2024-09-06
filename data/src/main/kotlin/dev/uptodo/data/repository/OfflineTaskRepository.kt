package dev.uptodo.data.repository

import dev.uptodo.data.local.dao.TaskDao
import dev.uptodo.data.local.entity.TaskEntity
import dev.uptodo.data.local.util.toTaskEntity
import dev.uptodo.data.util.getResult
import dev.uptodo.domain.model.Subtask
import dev.uptodo.domain.model.Task
import dev.uptodo.domain.model.TaskPriority
import dev.uptodo.domain.repository.OfflineTaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toLocalDateTime
import java.util.UUID
import javax.inject.Inject

class OfflineTaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : OfflineTaskRepository {

    override suspend fun getTasks(): List<Task> {
        return taskDao.getAllTasksWithCategories()
            .map { taskWithCategory -> taskWithCategory.toTask() }
    }

    override fun getCurrentDateTasks(): Flow<Map<String, Task>> {
        val currentDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        val (startOfDay, endOfDay) = currentDate.atTime(0, 0) to currentDate.atTime(23, 59)

        return taskDao.getTasksWithCategoriesByDate(startOfDay, endOfDay)
            .map { taskList ->
                taskList.associate { it.task.id to it.toTask() }
            }
    }

    override suspend fun updateTaskCompleteState(taskId: String): Result<Unit> {
        return getResult {
            taskDao.updateTaskCompleteState(taskId)
        }
    }

    override suspend fun createTask(
        name: String,
        description: String,
        priority: TaskPriority,
        categoryId: String?,
        subtasks: List<Subtask>,
        deadline: LocalDateTime
    ): Result<String> {
        return getResult {
            val taskId = UUID.randomUUID().toString()

            taskDao.createTask(
                TaskEntity(
                    id = taskId,
                    name = name,
                    description = description,
                    priority = priority,
                    subtasks = subtasks,
                    deadline = deadline,
                    categoryId = categoryId
                )
            )

            taskId
        }
    }

    override suspend fun deleteTask(id: String): Result<Unit> {
        return getResult {
            taskDao.deleteTaskById(id)
        }
    }

    override suspend fun updateTask(id: String, categoryId: String?, task: Task): Result<Unit> {
        return getResult {
            taskDao.updateTask(
                task.toTaskEntity(id, categoryId)
            )
        }
    }
}
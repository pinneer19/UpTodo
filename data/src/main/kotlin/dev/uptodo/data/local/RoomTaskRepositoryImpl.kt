package dev.uptodo.data.local

import dev.uptodo.data.model.TaskDto
import dev.uptodo.data.util.getResult
import dev.uptodo.data.util.toTaskDto
import dev.uptodo.domain.model.Subtask
import dev.uptodo.domain.model.Task
import dev.uptodo.domain.model.TaskPriority
import dev.uptodo.domain.repository.TaskRepository
import kotlinx.datetime.LocalDateTime
import java.util.UUID
import javax.inject.Inject

internal class RoomTaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {
    override suspend fun getTasks(): Result<Map<String, Task>> {
        return getResult<Map<String, Task>> {
            taskDao.getAllTasksWithCategories().associate { taskWithCategory ->
                val task = taskWithCategory.toTask()
                taskWithCategory.task.id to task
            }
        }
    }

    override suspend fun createTask(
        name: String,
        description: String,
        priority: TaskPriority,
        categoryId: String,
        subtasks: List<Subtask>,
        deadline: LocalDateTime
    ): Result<String> {
        return getResult<String> {
            val taskId = UUID.randomUUID().toString()

            taskDao.createTask(
                TaskDto(
                    taskId, name, description, priority, categoryId, subtasks, deadline
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

    override suspend fun updateTask(id: String, categoryId: String, task: Task): Result<Unit> {
        return getResult {
            taskDao.updateTask(
                task.toTaskDto(id, categoryId)
            )
        }
    }
}
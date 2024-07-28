package dev.uptodo.domain.repository

import dev.uptodo.domain.model.Subtask
import dev.uptodo.domain.model.Task
import dev.uptodo.domain.model.TaskCategory
import dev.uptodo.domain.model.TaskPriority
import kotlinx.datetime.LocalDateTime

interface TaskRepository {
    suspend fun getTasks(): Result<Map<String, Task>>

    suspend fun createTask(
        name: String,
        description: String,
        priority: TaskPriority,
        categoryId: String,
        subtasks: List<Subtask>,
        deadline: LocalDateTime
    ): Result<String>

    suspend fun deleteTask(id: String): Result<Unit>

    suspend fun updateTask(id: String, categoryId: String, task: Task): Result<Unit>
}

interface OfflineTaskRepository : TaskRepository

interface RemoteTaskRepository : TaskRepository
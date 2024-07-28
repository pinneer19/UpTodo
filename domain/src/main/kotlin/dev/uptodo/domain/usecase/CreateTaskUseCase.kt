package dev.uptodo.domain.usecase

import dev.uptodo.domain.model.Subtask
import dev.uptodo.domain.model.TaskPriority
import dev.uptodo.domain.repository.OfflineTaskRepository
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject

class CreateTaskUseCase @Inject constructor(
    private val repository: OfflineTaskRepository
) {
    suspend operator fun invoke(
        name: String,
        description: String,
        priority: TaskPriority,
        categoryId: String,
        subtasks: List<Subtask>,
        deadline: LocalDateTime
    ): Result<String> {
        return repository.createTask(name, description, priority, categoryId, subtasks, deadline)
    }
}
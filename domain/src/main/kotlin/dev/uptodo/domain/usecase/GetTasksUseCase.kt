package dev.uptodo.domain.usecase

import dev.uptodo.domain.model.Task
import dev.uptodo.domain.repository.OfflineTaskRepository
import dev.uptodo.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val taskRepository: OfflineTaskRepository
) {
    suspend operator fun invoke(): Flow<List<Task>> {
        return taskRepository.getTasks()
    }
}
package dev.uptodo.domain.usecase

import dev.uptodo.domain.model.Task
import dev.uptodo.domain.repository.OfflineTaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentDateTasksUseCase @Inject constructor(
    private val taskRepository: OfflineTaskRepository
) {
    operator fun invoke(): Flow<Map<String, Task>> {
        return taskRepository.getCurrentDateTasks()
    }
}
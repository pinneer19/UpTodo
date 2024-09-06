package dev.uptodo.domain.usecase

import dev.uptodo.domain.repository.OfflineTaskRepository
import javax.inject.Inject

class UpdateTaskCompleteStateUseCase @Inject constructor(
    private val repository: OfflineTaskRepository
) {
    suspend operator fun invoke(taskId: String): Result<Unit> {
        return repository.updateTaskCompleteState(taskId)
    }
}
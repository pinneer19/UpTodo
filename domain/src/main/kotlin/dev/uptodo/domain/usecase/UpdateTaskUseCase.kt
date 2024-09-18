package dev.uptodo.domain.usecase

import dev.uptodo.domain.model.Task
import dev.uptodo.domain.repository.OfflineTaskRepository
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val repository: OfflineTaskRepository
) {
    suspend operator fun invoke(id: String, categoryId: String?, task: Task): Result<Unit> {
        return repository.updateTask(id, categoryId, task)
    }
}
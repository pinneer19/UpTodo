package dev.uptodo.domain.usecase

import dev.uptodo.domain.repository.OfflineTaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val repository: OfflineTaskRepository
) {
    suspend operator fun invoke(id: String): Result<Unit> {
        return repository.deleteTask(id)
    }
}
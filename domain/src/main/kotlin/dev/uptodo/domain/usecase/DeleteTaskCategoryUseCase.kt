package dev.uptodo.domain.usecase

import dev.uptodo.domain.repository.OfflineTaskCategoryRepository
import javax.inject.Inject

class DeleteTaskCategoryUseCase @Inject constructor(
    private val repository: OfflineTaskCategoryRepository
) {
    suspend operator fun invoke(id: String): Result<Unit> {
        return repository.deleteTaskCategory(id)
    }
}
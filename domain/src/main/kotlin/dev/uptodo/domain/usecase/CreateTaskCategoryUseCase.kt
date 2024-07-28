package dev.uptodo.domain.usecase

import dev.uptodo.domain.repository.OfflineTaskCategoryRepository
import javax.inject.Inject

class CreateTaskCategoryUseCase @Inject constructor(
    private val repository: OfflineTaskCategoryRepository
) {
    suspend operator fun invoke(
        categoryName: String,
        iconUri: String,
        iconTint: String
    ): Result<String> {
        return repository.createTaskCategory(categoryName, iconUri, iconTint)
    }
}
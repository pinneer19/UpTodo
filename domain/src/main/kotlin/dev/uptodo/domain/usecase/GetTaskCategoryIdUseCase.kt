package dev.uptodo.domain.usecase

import dev.uptodo.domain.repository.OfflineTaskCategoryRepository
import javax.inject.Inject

class GetTaskCategoryIdUseCase @Inject constructor(
    private val offlineTaskCategoryRepository: OfflineTaskCategoryRepository
) {
    suspend operator fun invoke(categoryName: String): Result<String> {
        return offlineTaskCategoryRepository.getTaskCategoryIdByName(categoryName = categoryName)
    }
}
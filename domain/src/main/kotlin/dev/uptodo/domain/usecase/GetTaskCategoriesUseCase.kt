package dev.uptodo.domain.usecase

import dev.uptodo.domain.model.TaskCategory
import dev.uptodo.domain.repository.OfflineTaskCategoryRepository
import javax.inject.Inject

class GetAllTaskCategoriesUseCase @Inject constructor(
    private val taskCategoryRepository: OfflineTaskCategoryRepository
) {
    suspend operator fun invoke(): Result<Map<String, TaskCategory>> {
        return taskCategoryRepository.getTaskCategories()
    }
}

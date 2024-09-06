package dev.uptodo.domain.usecase

import dev.uptodo.domain.model.TaskCategory
import dev.uptodo.domain.repository.OfflineTaskCategoryRepository
import dev.uptodo.domain.repository.TaskCategoryRepository
import javax.inject.Inject

class GetTaskCategoriesUseCase @Inject constructor(
    private val taskCategoryRepository: OfflineTaskCategoryRepository
) {
    suspend operator fun invoke(): Result<Map<String, TaskCategory>> {
        return taskCategoryRepository.getTaskCategories()
    }
}

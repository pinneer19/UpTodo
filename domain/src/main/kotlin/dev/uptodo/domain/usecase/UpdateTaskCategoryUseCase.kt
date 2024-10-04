package dev.uptodo.domain.usecase

import dev.uptodo.domain.model.TaskCategory
import dev.uptodo.domain.repository.OfflineTaskCategoryRepository
import dev.uptodo.domain.repository.TaskCategoryRepository
import javax.inject.Inject

class UpdateTaskCategoryUseCase @Inject constructor(
    private val taskCategoryRepository: OfflineTaskCategoryRepository
) {
    suspend operator fun invoke(id: String, category: TaskCategory) {
        taskCategoryRepository.updateTaskCategory(id, category)
    }
}

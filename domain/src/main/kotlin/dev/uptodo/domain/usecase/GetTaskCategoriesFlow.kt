package dev.uptodo.domain.usecase

import dev.uptodo.domain.model.TaskCategory
import dev.uptodo.domain.repository.OfflineTaskCategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTaskCategoriesFlowUseCase @Inject constructor(
    private val taskCategoryRepository: OfflineTaskCategoryRepository
) {
    operator fun invoke(): Flow<Map<String, TaskCategory>> {
        return taskCategoryRepository.getTaskCategoriesFlow()
    }
}

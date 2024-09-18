package dev.uptodo.domain.usecase

import dev.uptodo.domain.repository.OfflineTaskCategoryRepository
import javax.inject.Inject

class GetIconNamesUseCase @Inject constructor(
    private val taskCategoryRepository: OfflineTaskCategoryRepository
) {
    operator fun invoke(): List<String> {
        return taskCategoryRepository.getIconNames()
    }
}

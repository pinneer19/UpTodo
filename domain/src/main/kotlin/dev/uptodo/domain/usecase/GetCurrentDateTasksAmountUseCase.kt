package dev.uptodo.domain.usecase

import dev.uptodo.domain.repository.OfflineTaskRepository
import javax.inject.Inject

class GetCurrentDateTasksAmountUseCase @Inject constructor(
    private val taskRepository: OfflineTaskRepository
) {
    suspend operator fun invoke(): Result<Int> {
        return taskRepository.getCurrentDateTasksAmount()
    }
}

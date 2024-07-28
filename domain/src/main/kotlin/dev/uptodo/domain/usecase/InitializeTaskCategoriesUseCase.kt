package dev.uptodo.domain.usecase

import dev.uptodo.domain.model.TaskCategory
import dev.uptodo.domain.repository.OfflineTaskCategoryRepository
import java.io.FileReader
import javax.inject.Inject

class InitializeTaskCategoriesUseCase @Inject constructor(
    private val repository: OfflineTaskCategoryRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.initializeTaskCategories(DEFAULT_CATEGORIES)
    }

    companion object {
        private val DEFAULT_CATEGORIES: List<TaskCategory> = readTaskCategoriesFromFile()

        private fun readTaskCategoriesFromFile(): List<TaskCategory> {
            return FileReader("../util/default_categories.txt")
                .readLines()
                .map { line ->
                    val (name, iconUri, iconTint) = line.split(", ")
                    TaskCategory(name, iconUri, iconTint)
                }
        }
    }
}
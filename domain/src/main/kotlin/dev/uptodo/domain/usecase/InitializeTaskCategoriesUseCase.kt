package dev.uptodo.domain.usecase

import dev.uptodo.domain.model.TaskCategory
import dev.uptodo.domain.repository.OfflineTaskCategoryRepository
import javax.inject.Inject

class InitializeTaskCategoriesUseCase @Inject constructor(
    private val repository: OfflineTaskCategoryRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.initializeTaskCategories(DEFAULT_CATEGORIES)
    }

    companion object {
        private val DEFAULT_CATEGORIES: List<TaskCategory> = listOf(
            TaskCategory(
                name = "Grocery",
                iconUri = "Icons.Default.ShoppingCart",
                iconTint = "#FF6347"
            ),
            TaskCategory(
                name = "Work",
                iconUri = "Icons.Default.Work",
                iconTint = "#4682B4"
            ),
            TaskCategory(
                name = "Sport",
                iconUri = "Icons.Default.FitnessCenter",
                iconTint = "#32CD32"
            ),
            TaskCategory(
                name = "Design",
                iconUri = "Icons.Default.Palette",
                iconTint = "#FF69B4"
            ),
            TaskCategory(
                name = "University",
                iconUri = "Icons.Default.School",
                iconTint = "#B478ED"
            ),
            TaskCategory(
                name = "Social",
                iconUri = "Icons.Default.People",
                iconTint = "#FFD700"
            ),
            TaskCategory(
                name = "Music",
                iconUri = "Icons.Default.MusicNote",
                iconTint = "#9999FF"
            ),
            TaskCategory(
                name = "Health",
                iconUri = "Icons.Default.HealthAndSafety",
                iconTint = "#008080"
            ),
            TaskCategory(
                name = "Movie",
                iconUri = "Icons.Default.Movie",
                iconTint = "#DA3E3E"
            ),
            TaskCategory(
                name = "Home",
                iconUri = "Icons.Default.Home",
                iconTint = "#2E8B57"
            )
        )
    }
}
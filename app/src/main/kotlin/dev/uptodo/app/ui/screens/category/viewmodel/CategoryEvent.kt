package dev.uptodo.app.ui.screens.category.viewmodel

import androidx.compose.ui.graphics.Color

sealed class CategoryEvent {

    data class UpdateCategoryName(val name: String): CategoryEvent()

    data class PickCategoryIcon(val icon: String): CategoryEvent()

    data class UpdateCategoryColor(val color: Color?): CategoryEvent()

    data class UpdateIconPickerSearchInput(val searchInput: String): CategoryEvent()

    data object DeleteCategory: CategoryEvent()

    data object SaveCategory: CategoryEvent()

    data object CreateCategory : CategoryEvent()
}
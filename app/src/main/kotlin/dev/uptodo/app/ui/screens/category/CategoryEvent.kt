package dev.uptodo.app.ui.screens.category

import androidx.compose.ui.graphics.Color

sealed class CategoryEvent {

    data class UpdateCategoryName(val name: String): CategoryEvent()

    data object CreateCategory : CategoryEvent()

    data class PickCategoryIcon(val icon: String): CategoryEvent()

    data class UpdateCategoryColor(val color: Color?): CategoryEvent()

    data class UpdateIconPickerSearchInput(val searchInput: String): CategoryEvent()
}
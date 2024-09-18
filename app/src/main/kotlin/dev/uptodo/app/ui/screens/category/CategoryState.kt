package dev.uptodo.app.ui.screens.category

import androidx.compose.ui.graphics.Color

data class CategoryState(
    val name: String = "",
    val iconUri: String = "",
    val color: Color? = null,
    val iconPickerItems: List<List<IconItem>> = emptyList(),
    val iconPickerSearchInput: String = ""
)

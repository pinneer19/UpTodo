package dev.uptodo.app.ui.screens.category.viewmodel

import androidx.compose.ui.graphics.Color
import dev.uptodo.app.ui.screens.category.dialog.icon.IconItem
import dev.uptodo.app.ui.util.UiText

data class CategoryState(
    val name: String = "",
    val iconUri: String = "",
    val color: Color? = null,
    val iconPickerItems: List<List<IconItem>> = emptyList(),
    val iconPickerSearchInput: String = "",
    val error: UiText? = null,
    val editMode: Boolean = false
)
package dev.uptodo.app.ui.screens.task.fab

import androidx.compose.ui.graphics.vector.ImageVector

data class FabAction(
    val name: String,
    val icon: ImageVector,
    val onAction: () -> Unit = {}
)
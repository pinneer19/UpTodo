package dev.uptodo.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavigation(val label: String, val icon: ImageVector, val route: Route) {
    HOME("Home", Icons.Default.Home, Route.Home)
}
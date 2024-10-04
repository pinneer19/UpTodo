package dev.uptodo.app.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import dev.uptodo.app.R

enum class BottomNavigation(
    @StringRes val labelResId: Int,
    val icon: ImageVector,
    val route: Route
) {
    HOME(R.string.home, Icons.Default.Home, MainRoute.Home);

    override fun toString(): String {
        return this::class.qualifiedName.toString()
    }
}
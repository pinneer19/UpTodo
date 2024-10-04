package dev.uptodo.app.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource

@Composable
fun MainBottomBar(
    currentRoute: String,
    onNavigationBarItemClick: (Route) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        BottomNavigation.entries.forEach { navigationItem ->
            val isSelected by remember(currentRoute) {
                derivedStateOf { currentRoute == navigationItem.route::class.qualifiedName }
            }

            val label = stringResource(id = navigationItem.labelResId)

            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                ),
                selected = isSelected,
                label = {
                    Text(text = label)
                },
                onClick = {
                    onNavigationBarItemClick(navigationItem.route)
                },
                icon = {
                    Icon(
                        imageVector = navigationItem.icon,
                        contentDescription = label
                    )
                }
            )
        }
    }
}
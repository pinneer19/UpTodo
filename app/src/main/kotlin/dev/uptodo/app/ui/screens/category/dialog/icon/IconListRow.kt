package dev.uptodo.app.ui.screens.category.dialog.icon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun IconListRow(
    icons: List<IconItem>,
    selectedIcon: String,
    onClick: (IconItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        icons.forEach {
            IconPickerItem(
                icon = it,
                selected = it.id == selectedIcon,
                onClick = { onClick(it) }
            )
        }
    }
}
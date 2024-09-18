package dev.uptodo.app.ui.screens.category

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun IconsList(
    modifier: Modifier = Modifier,
    icons: List<List<IconItem>>,
    selectedIcon: String,
    onIconClick: (IconItem) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(
            key = { row -> row.joinToString { it.id } },
            items = icons
        ) { rowItems ->
            IconListRow(
                modifier = Modifier.animateItem(),
                icons = rowItems,
                selectedIcon = selectedIcon,
                onClick = { item -> onIconClick(item) }
            )
        }
    }
}
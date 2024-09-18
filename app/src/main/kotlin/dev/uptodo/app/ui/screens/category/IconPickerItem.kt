package dev.uptodo.app.ui.screens.category

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun IconPickerItem(
    icon: IconItem, selected: Boolean, onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surfaceContainer,
        label = "Icon picker item background animation"
    )

    val tint by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.primary,
        label = "Icon picker item tint animation"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(96.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .clickable { onClick() },
    ) {
        Icon(
            imageVector = icon.uri,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(40.dp)
        )
    }
}
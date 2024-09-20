package dev.uptodo.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.uptodo.app.ui.util.darken

@Composable
fun CategoryItem(
    title: String,
    imageVector: ImageVector,
    tint: Color,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val defaultAnimationSpec: AnimationSpec<Color> = tween(durationMillis = 200)

    Column(
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(color = tint, shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = tint.darken(0.5f)
            )
        }

        Text(
            text = title,
            modifier = Modifier
                .padding(top = 5.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(
                    color = animateColorAsState(
                        targetValue = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent,
                        animationSpec = defaultAnimationSpec,
                        label = "animate selected item background"
                    ).value
                )
                .padding(vertical = 1.dp, horizontal = 8.dp),
            color = animateColorAsState(
                targetValue = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                animationSpec = defaultAnimationSpec,
                label = "animate selected item text color"
            ).value
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryItemPreview() {
    CategoryItem(
        title = "Test",
        imageVector = Icons.Default.Home,
        tint = Color.Blue,
        selected = false,
        onClick = {}
    )
}
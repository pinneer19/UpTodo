package dev.uptodo.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PriorityItem(
    priorityNumber: Int,
    selected: Boolean,
    onSelect: () -> Unit
) {
    val defaultAnimationSpec: AnimationSpec<Color> = tween(durationMillis = 200)

    val background = animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f)
        },
        animationSpec = defaultAnimationSpec,
        label = "animate selected priority background"
    ).value

    val targetColor = if (selected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onBackground
    }

    Column(
        modifier = Modifier
            .size(64.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = background)
            .clickable { onSelect() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.Flag,
            tint = animateColorAsState(
                targetValue = targetColor,
                animationSpec = defaultAnimationSpec,
                label = "animate selected priority icon tint"
            ).value,
            modifier = Modifier
                .padding(bottom = 7.dp)
                .align(Alignment.CenterHorizontally),
            contentDescription = null
        )

        Text(
            text = priorityNumber.toString(),
            color = animateColorAsState(
                targetValue = targetColor,
                animationSpec = defaultAnimationSpec,
                label = "animate selected priority text color"
            ).value,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
    }
}

@Preview
@Composable
private fun PriorityItemPreview() {
    PriorityItem(
        priorityNumber = 1,
        onSelect = {},
        selected = true
    )
}
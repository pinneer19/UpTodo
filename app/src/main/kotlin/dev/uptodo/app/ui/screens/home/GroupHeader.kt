package dev.uptodo.app.ui.screens.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate

@Composable
fun GroupHeader(
    header: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    expanded: Boolean
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(header)

        val rotation by animateFloatAsState(
            targetValue = if (expanded) 0f else -180f,
            animationSpec = tween(500),
            label = "rotateArrowAnimation"
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            modifier = Modifier.rotate(rotation)
        )
    }
}
package dev.uptodo.app.ui.screens.home.component

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
    val rotation by animateFloatAsState(
        targetValue = if (expanded) 0f else -180f,
        animationSpec = tween(500),
        label = "rotateArrowAnimation"
    )

    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(header)

        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            modifier = Modifier.rotate(rotation)
        )
    }
}
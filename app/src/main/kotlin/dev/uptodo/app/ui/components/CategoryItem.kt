package dev.uptodo.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import dev.uptodo.app.R
import dev.uptodo.app.ui.util.darken

@Composable
fun CategoryItem(
    title: String,
    imageVector: ImageVector,
    tint: Color,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    showLongPressMenu: Boolean = true,
    onMenuEditAction: () -> Unit = {},
    onMenuDeleteAction: () -> Unit = {},
) {
    var menuVisible by remember { mutableStateOf(false) }
    var itemHeight by remember { mutableStateOf(0.dp) }
    var pressOffset by remember { mutableStateOf(DpOffset.Zero) }

    val density = LocalDensity.current
    val defaultAnimationSpec: AnimationSpec<Color> = tween(durationMillis = 200)

    Column(
        modifier = modifier
            .pointerInput(true) {
                detectTapGestures(
                    onLongPress = { offset ->
                        menuVisible = true
                        pressOffset = DpOffset(offset.x.toDp(), offset.y.toDp())
                    },
                    onTap = { onClick() }
                )
            }
            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            },
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

        if (showLongPressMenu) {
            DropdownMenu(
                expanded = menuVisible,
                onDismissRequest = { menuVisible = false },
                shadowElevation = 8.dp,
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                offset = pressOffset.copy(y = pressOffset.y - itemHeight)
            ) {
                DropdownMenuItem(
                    text = {
                        Text(text = stringResource(id = R.string.edit))
                    },
                    onClick = {
                        onMenuEditAction()
                        menuVisible = false
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text(text = stringResource(id = R.string.delete))
                    },
                    onClick = {
                        onMenuDeleteAction()
                        menuVisible = false
                    }
                )

            }
        }
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
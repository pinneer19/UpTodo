package dev.uptodo.app.ui.screens.category

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.uptodo.app.ui.components.CategoryItem
import dev.uptodo.app.ui.theme.UpTodoTheme
import dev.uptodo.app.util.decodeMaterialIcon
import dev.uptodo.app.util.keyboardAsState
import kotlin.math.roundToInt

@Composable
fun CategoryScreen(
    uiState: CategoryState,
    onEvent: (CategoryEvent) -> Unit,
    onNavigateUp: () -> Unit
) {
    var showColorPicker by remember { mutableStateOf(false) }
    var showIconPicker by remember { mutableStateOf(false) }

    val animatableTint = uiState.color ?: Color.Transparent

    val categoryItemPreviewTint by animateColorAsState(
        targetValue = animatableTint,
        label = "Category item preview tint animation"
    )

    val isKeyboardOpen by keyboardAsState()

    val alpha by animateFloatAsState(
        targetValue = if (isKeyboardOpen) 0f else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "Category item preview alpha animation"
    )

    val offset by animateFloatAsState(
        targetValue = if (isKeyboardOpen) -100f else 0f,
        animationSpec = tween(durationMillis = 150),
        label = "Category item preview offset animation"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(start = 24.dp, end = 24.dp, top = 26.dp)
            .windowInsetsPadding(insets = WindowInsets(bottom = 35.dp))
            .imePadding()
    ) {
        Text(
            text = "Create new category",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = "Name",
            modifier = Modifier.padding(top = 40.dp, bottom = 10.dp),
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.onSurface
        )

        OutlinedTextField(
            value = uiState.name,
            placeholder = { Text(text = "Category name") },
            onValueChange = { onEvent(CategoryEvent.UpdateCategoryName(it)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(6.dp),
            singleLine = true
        )

        Text(
            text = "Icon",
            modifier = Modifier.padding(top = 30.dp, bottom = 10.dp),
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.onSurface
        )

        Button(
            onClick = { showIconPicker = true },
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Choose icon from library")
        }

        Text(
            text = "Color",
            modifier = Modifier.padding(top = 30.dp, bottom = 10.dp),
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.onSurface
        )

        Button(
            onClick = { showColorPicker = true },
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Show color picker")
        }

        if (showColorPicker) {
            ColorPickerDialog(
                colorState = uiState.color,
                onEvent = onEvent,
                onDismissRequest = { showColorPicker = !showColorPicker }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (alpha > 0f) {
            CategoryItem(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .scale(2f)
                    .alpha(alpha)
                    .offset { IntOffset(x = 0, y = offset.roundToInt()) },
                title = uiState.name,
                imageVector = decodeMaterialIcon(uiState.iconUri),
                tint = categoryItemPreviewTint,
                selected = false
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TextButton(
                onClick = onNavigateUp,
                modifier = Modifier.size(width = 140.dp, height = 45.dp)
            ) {
                Text(text = "Cancel")
            }

            Button(
                onClick = {
                    onEvent(CategoryEvent.CreateCategory)
                    onNavigateUp()
                },
                modifier = Modifier.size(width = 140.dp, height = 45.dp)
            ) {
                Text(text = "Create")
            }
        }
    }

    if (showIconPicker) {
        IconPickerDialog(
            icons = uiState.iconPickerItems,
            onDismissRequest = { showIconPicker = !showIconPicker },
            onEvent = onEvent
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun CategoryScreenPreview() {
    UpTodoTheme(darkTheme = true) {
        CategoryScreen(uiState = CategoryState(), onEvent = {}, onNavigateUp = {})
    }
}
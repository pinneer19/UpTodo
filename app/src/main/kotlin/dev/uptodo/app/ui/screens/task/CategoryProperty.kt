package dev.uptodo.app.ui.screens.task

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.uptodo.app.R
import dev.uptodo.app.ui.components.PickerDialog
import dev.uptodo.app.ui.components.CategoryItem
import dev.uptodo.app.util.decodeMaterialIcon
import dev.uptodo.app.util.fromHex
import dev.uptodo.domain.model.TaskCategory

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryProperty(
    categoryId: String?,
    availableCategories: Map<String, TaskCategory>,
    onSaveCategory: (String?) -> Unit,
    onAddCategory: () -> Unit
) {
    var showCategoryPicker by remember {
        mutableStateOf(false)
    }

    categoryId?.let {
        val category =
            requireNotNull(availableCategories.entries.find { entry -> entry.key == it }?.value)

        TaskProperty(
            name = "Category",
            value = category.name,
            icon = Icons.Outlined.Category,
            valueIcon = decodeMaterialIcon(category.iconUri),
            onPropertyClicked = { showCategoryPicker = true }
        )
    } ?:
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Category,
                contentDescription = null,
                modifier = Modifier.padding(end = 10.dp)
            )

            Text(
                text = stringResource(R.string.category),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.weight(1f))

            TextButton(onClick = { showCategoryPicker = true }) {
                Text(text = stringResource(R.string.select_category))
            }
        }

    if (showCategoryPicker) {
        var selectedCategory by remember {
            mutableStateOf(categoryId)
        }

        PickerDialog(
            title = stringResource(R.string.task_category),
            onDismissRequest = { showCategoryPicker = false },
            onConfirm = {
                onSaveCategory(selectedCategory)
                showCategoryPicker = false
            },
            pickerContent = {
                availableCategories.entries.forEach {
                    with(it.value) {
                        val imageVector = decodeMaterialIcon(iconUri)

                        CategoryItem(
                            title = name,
                            imageVector = imageVector,
                            tint = Color.fromHex(iconTint),
                            selected = selectedCategory == it.key,
                            onClick = { selectedCategory = it.key },
                        )
                    }
                }

                CategoryItem(
                    title = stringResource(R.string.add_new_category),
                    imageVector = Icons.Default.Add,
                    tint = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.7f),
                    selected = false,
                    onClick = {
                        showCategoryPicker = !showCategoryPicker
                        onAddCategory()
                    }
                )
            },
        )
    }
}
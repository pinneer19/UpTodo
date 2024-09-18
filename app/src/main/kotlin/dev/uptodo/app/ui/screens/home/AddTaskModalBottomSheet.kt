package dev.uptodo.app.ui.screens.home

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import dev.uptodo.app.R
import dev.uptodo.app.ui.components.CategoryItem
import dev.uptodo.app.ui.components.PickerDialog
import dev.uptodo.app.ui.components.PriorityItem
import dev.uptodo.app.ui.components.TimePickerDialog
import dev.uptodo.app.util.decodeMaterialIcon
import dev.uptodo.app.util.fromHex
import dev.uptodo.domain.model.TaskCategory

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddTaskModalBottomSheet(
    title: String,
    description: String,
    priority: Int?,
    categoryId: String,
    categories: Map<String, TaskCategory>,
    onDismissRequest: () -> Unit,
    onEvent: (HomeEvent) -> Unit,
    onAddTaskCategory: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val timePickerState = rememberTimePickerState()
    val datePickerState = rememberDatePickerState()

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showCategoryPicker by remember { mutableStateOf(false) }
    var showPriorityPicker by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        contentWindowInsets = { WindowInsets.ime }
    ) {
        SheetMainContent(
            title = title,
            description = description,
            showDatePicker = { showDatePicker = !showDatePicker },
            showCategoryPicker = { showCategoryPicker = !showCategoryPicker },
            showPriorityPicker = { showPriorityPicker = !showPriorityPicker },
            onUpdateSheetTaskTitle = { title -> onEvent(HomeEvent.UpdateSheetTaskTitle(title)) },
            onUpdateSheetTaskDescription = { description ->
                onEvent(
                    HomeEvent.UpdateSheetTaskDescription(description)
                )
            },
            onCreateTask = {
                onEvent(HomeEvent.CreateTask)
                onDismissRequest()
            }
        )

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDatePicker = false
                            showTimePicker = true
                        }
                    ) {
                        Text(text = stringResource(R.string.edit_time))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text(text = stringResource(R.string.cancel))
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        if (showTimePicker) {
            TimePickerDialog(
                onDismiss = { showTimePicker = false },
                onConfirm = {
                    with(timePickerState) {
                        onEvent(
                            HomeEvent.UpdateSheetTaskDeadline(
                                date = datePickerState.selectedDateMillis,
                                hour = hour,
                                minute = minute
                            )
                        )
                    }
                    showTimePicker = false
                }
            ) {
                TimePicker(state = timePickerState)
            }
        }

        if (showPriorityPicker) {
            var selectedPriority by remember { mutableStateOf(priority) }

            PickerDialog(
                title = stringResource(R.string.task_priority),
                onDismissRequest = { showPriorityPicker = false },
                onConfirm = {
                    onEvent(HomeEvent.UpdateSheetTaskPriority(selectedPriority))
                    showPriorityPicker = false
                },
                pickerContent = {
                    (1..10).forEach {
                        PriorityItem(
                            priorityNumber = it,
                            selected = selectedPriority == it,
                            onSelect = { selectedPriority = it }
                        )
                    }
                },
            )
        }

        if (showCategoryPicker) {
            var selectedCategory by remember { mutableStateOf(categoryId) }

            PickerDialog(
                title = stringResource(R.string.task_category),
                onDismissRequest = { showCategoryPicker = false },
                onConfirm = {
                    onEvent(HomeEvent.UpdateSheetTaskCategory(selectedCategory))
                    showCategoryPicker = false
                },
                pickerContent = {
                    categories.entries.forEach { entry ->
                        with(entry.value) {
                            val imageVector = decodeMaterialIcon(iconUri)

                            CategoryItem(
                                title = name,
                                imageVector = imageVector,
                                tint = Color.fromHex(iconTint),
                                selected = selectedCategory == entry.key,
                                onClick = { selectedCategory = entry.key },
                            )
                        }
                    }

                    CategoryItem(
                        title = stringResource(R.string.add_new_category),
                        imageVector = Icons.Default.Add,
                        tint = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.7f),
                        selected = false,
                        onClick = onAddTaskCategory
                    )
                },
            )
        }
    }
}
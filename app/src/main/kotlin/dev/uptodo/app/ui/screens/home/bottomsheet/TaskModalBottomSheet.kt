package dev.uptodo.app.ui.screens.home.bottomsheet

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import dev.uptodo.app.R
import dev.uptodo.app.ui.components.CategoryItem
import dev.uptodo.app.ui.components.PickerDialog
import dev.uptodo.app.ui.components.PriorityItem
import dev.uptodo.app.ui.components.TimePickerDialog
import dev.uptodo.app.ui.screens.home.bottomsheet.viewmodel.TaskSheetEvent
import dev.uptodo.app.ui.screens.home.bottomsheet.viewmodel.TaskSheetState
import dev.uptodo.app.ui.util.decodeMaterialIcon
import dev.uptodo.app.ui.util.fromHex
import dev.uptodo.app.util.Constants
import dev.uptodo.app.util.helper.isInternetAvailable
import dev.uptodo.app.util.reminder.scheduleTaskReminder
import dev.uptodo.domain.model.TaskCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskModalBottomSheet(
    uiState: TaskSheetState,
    onEvent: (TaskSheetEvent) -> Unit,
    onHideBottomSheet: () -> Unit,
    onNavigateToCategoryScreen: (categoryId: String?, category: TaskCategory?) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()

    val timePickerState = rememberTimePickerState()
    val datePickerState = rememberDatePickerState(selectableDates = Constants.SelectableDates)

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showCategoryPicker by remember { mutableStateOf(false) }
    var showPriorityPicker by remember { mutableStateOf(false) }

    val languageCode = Locale.current.language
    val context = LocalContext.current

    ModalBottomSheet(
        onDismissRequest = onHideBottomSheet,
        sheetState = sheetState,
        containerColor = Color.White,
        contentWindowInsets = { WindowInsets.ime }
    ) {
        TaskSheetContent(
            title = uiState.title,
            description = uiState.description,
            speaking = uiState.speaking,
            showDatePicker = { showDatePicker = true },
            showCategoryPicker = { showCategoryPicker = true },
            showPriorityPicker = { showPriorityPicker = true },
            onUpdateTitle = { title -> onEvent(TaskSheetEvent.UpdateTitle(title)) },
            onUpdateDescription = { description ->
                onEvent(
                    TaskSheetEvent.UpdateDescription(description)
                )
            },
            onCreateTask = {
                onEvent(
                    TaskSheetEvent.CreateTask(
                        onCreateTaskReminder = { taskId, taskTitle, taskDeadline ->
                            onHideBottomSheet()

                            scheduleTaskReminder(
                                context = context,
                                taskId = taskId,
                                taskTitle = taskTitle,
                                taskDeadline = taskDeadline
                            )
                        }
                    )
                )
            },
            onRecordVoice = {
                when {
                    !isInternetAvailable(context) -> {
                        onEvent(
                            TaskSheetEvent.UpdateError(
                                message = context.getString(R.string.enable_network)
                            )
                        )
                    }

                    uiState.speaking -> {
                        onEvent(TaskSheetEvent.StopRecording)
                    }

                    else -> {
                        onEvent(
                            TaskSheetEvent.RecordVoice(languageCode = languageCode)
                        )
                    }
                }
            },
            error = uiState.error
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
                            TaskSheetEvent.UpdateDeadline(
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
            var selectedPriority by remember {
                mutableStateOf(uiState.priority.toInt())
            }

            PickerDialog(
                title = stringResource(R.string.task_priority),
                onDismissRequest = { showPriorityPicker = false },
                onConfirm = {
                    onEvent(
                        TaskSheetEvent.UpdatePriority(selectedPriority)
                    )
                    showPriorityPicker = false
                },
                pickerContent = {
                    items(count = 10) {
                        PriorityItem(
                            priorityNumber = it + 1,
                            selected = selectedPriority == it + 1,
                            onSelect = { selectedPriority = it + 1 }
                        )
                    }
                },
            )
        }

        if (showCategoryPicker) {
            var selectedCategory by remember { mutableStateOf(uiState.categoryId) }

            PickerDialog(
                title = stringResource(R.string.task_category),
                onDismissRequest = { showCategoryPicker = false },
                onConfirm = {
                    onEvent(
                        TaskSheetEvent.UpdateCategory(selectedCategory)
                    )
                    showCategoryPicker = false
                },
                pickerContent = {
                    items(
                        items = uiState.categories.entries.toList(),
                        key = { entry -> entry.key }
                    ) { entry ->
                        with(entry.value) {
                            val imageVector = decodeMaterialIcon(iconUri)

                            CategoryItem(
                                modifier = Modifier.animateItem(),
                                title = name,
                                imageVector = imageVector,
                                tint = Color.fromHex(iconTint),
                                selected = selectedCategory == entry.key,
                                onClick = {
                                    selectedCategory =
                                        if (entry.key == selectedCategory) {
                                            null
                                        } else {
                                            entry.key
                                        }
                                },
                                onMenuDeleteAction = {
                                    onEvent(
                                        TaskSheetEvent.DeleteTaskCategory(entry.key)
                                    )
                                },
                                onMenuEditAction = {
                                    onHideBottomSheet()
                                    onNavigateToCategoryScreen(entry.key, entry.value)
                                }
                            )
                        }

                    }

                    item(key = R.string.add_new_category) {
                        CategoryItem(
                            title = stringResource(R.string.add_new_category),
                            imageVector = Icons.Default.Add,
                            tint = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.7f),
                            selected = false,
                            onClick = {
                                onHideBottomSheet()
                                onNavigateToCategoryScreen(null, null)
                            },
                            showLongPressMenu = false,
                            modifier = Modifier.animateItem()
                        )
                    }
                }
            )
        }
    }
}
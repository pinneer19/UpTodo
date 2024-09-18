package dev.uptodo.app.ui.screens.task

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import dev.uptodo.app.R
import dev.uptodo.app.ui.components.PickerDialog
import dev.uptodo.app.ui.components.PriorityItem
import dev.uptodo.domain.model.TaskPriority

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PriorityProperty(priority: TaskPriority, onEvent: (TaskEvent) -> Unit) {

    var showPriorityPicker by remember { mutableStateOf(false)}

    TaskProperty(
        name = stringResource(R.string.priority),
        value = priority.toString(),
        icon = Icons.Outlined.Flag,
        onPropertyClicked = { showPriorityPicker = true }
    )

    if (showPriorityPicker) {
        var selectedPriority by remember { mutableStateOf(priority.toInt()) }

        PickerDialog(
            title = stringResource(R.string.task_priority),
            onDismissRequest = { showPriorityPicker = false },
            onConfirm = {
                onEvent(TaskEvent.UpdateTaskPriority(selectedPriority))
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
}
package dev.uptodo.app.ui.screens.task

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import dev.uptodo.app.R
import dev.uptodo.app.ui.components.TimePickerDialog
import dev.uptodo.app.util.toUiString
import kotlinx.datetime.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeProperty(deadline: LocalDateTime?, onEvent: (TaskEvent) -> Unit) {

    var showTimePicker by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()

    deadline?.let {
        TaskProperty(
            name = "Time",
            value = it.toUiString(considerDate = true),
            icon = Icons.Outlined.Timer,
            onPropertyClicked = { showDatePicker = true }
        )
    } ?:
        TextButton(onClick = { showDatePicker = true }) {
            Text(
                text = stringResource(id = R.string.select_category),
                style = MaterialTheme.typography.bodyLarge
            )
        }

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
                        TaskEvent.UpdateTaskDeadline(
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
}
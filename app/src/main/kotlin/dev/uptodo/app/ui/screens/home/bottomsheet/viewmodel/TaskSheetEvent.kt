package dev.uptodo.app.ui.screens.home.bottomsheet.viewmodel

import kotlinx.datetime.LocalDateTime

sealed class TaskSheetEvent {
    data class UpdateTitle(val title: String) : TaskSheetEvent()

    data class UpdateDescription(val description: String) : TaskSheetEvent()

    data class UpdateDeadline(
        val date: Long?,
        val hour: Int,
        val minute: Int
    ) : TaskSheetEvent()

    data class UpdateCategory(val categoryId: String?) : TaskSheetEvent()

    data class UpdatePriority(val priority: Int?) : TaskSheetEvent()

    data class CreateTask(
        val onCreateTaskReminder: (String, String, LocalDateTime) -> Unit
    ) : TaskSheetEvent()

    data class UpdateError(val message: String) : TaskSheetEvent()

    data class DeleteTaskCategory(val categoryId: String) : TaskSheetEvent()

    data class RecordVoice(val languageCode: String) : TaskSheetEvent()

    data object StopRecording : TaskSheetEvent()
}
package dev.uptodo.app.ui.screens.home

sealed class HomeEvent {

    data class UpdateSheetTaskTitle(val title: String) : HomeEvent()

    data class UpdateSheetTaskDescription(val description: String) : HomeEvent()

    data class UpdateSheetTaskDeadline(val date: Long?, val hour: Int, val minute: Int) : HomeEvent()

    data class UpdateSheetTaskCategory(val categoryId: String) : HomeEvent()

    data class UpdateSheetTaskPriority(val priority: Int?) : HomeEvent()

    data class ToggleTaskCompleteState(val taskId: String) : HomeEvent()

    data class DeleteTask(val taskId: String) : HomeEvent()

    data object CreateTask : HomeEvent()
}
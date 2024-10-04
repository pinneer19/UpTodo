package dev.uptodo.app.ui.screens.home.viewmodel

import dev.uptodo.app.ui.util.UiText

sealed class HomeEvent {

    data class ToggleTaskCompleteState(val taskId: String) : HomeEvent()

    data class DeleteTask(val taskId: String) : HomeEvent()

    data class DeleteTaskCategory(val categoryId: String) : HomeEvent()

    data class UpdateError(val message: UiText?) : HomeEvent()

    data object ClearError : HomeEvent()

    data object ShowBottomSheet : HomeEvent()

    data object HideBottomSheet : HomeEvent()
}
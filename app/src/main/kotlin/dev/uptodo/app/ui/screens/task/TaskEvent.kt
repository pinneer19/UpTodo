package dev.uptodo.app.ui.screens.task

sealed class TaskEvent {

    data class UpdateTaskName(val name: String) : TaskEvent()

    data class UpdateTaskDescription(val description: String) : TaskEvent()

    data class UpdateTaskDeadline(val date: Long?, val hour: Int, val minute: Int) : TaskEvent()

    data class UpdateTaskPriority(val priority: Int?) : TaskEvent()

    data class UpdateTaskCategory(val categoryId: String?) : TaskEvent()

    data class RemoveSubtask(val index: Int) : TaskEvent()

    data class UpdateSubtask(val index: Int, val subtaskName: String) : TaskEvent()

    data object AddSubtask : TaskEvent()

    data object DeleteTask : TaskEvent()

    data object EditTask : TaskEvent()
}
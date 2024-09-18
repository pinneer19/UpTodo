package dev.uptodo.app.ui.screens.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.uptodo.app.util.default
import dev.uptodo.domain.model.Subtask
import dev.uptodo.domain.model.Task
import dev.uptodo.domain.model.toTaskPriority
import dev.uptodo.domain.usecase.DeleteTaskUseCase
import dev.uptodo.domain.usecase.GetAllTaskCategoriesUseCase
import dev.uptodo.domain.usecase.GetTaskCategoryIdUseCase
import dev.uptodo.domain.usecase.UpdateTaskUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

class TaskViewModel @Inject constructor(
    private val getCategoriesUseCase: GetAllTaskCategoriesUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getTaskCategoryIdUseCase: GetTaskCategoryIdUseCase,
    private val taskId: String,
    private val initialTask: Task
) : ViewModel() {

    private val _uiState: MutableStateFlow<TaskState> = with(initialTask) {
        MutableStateFlow(
            TaskState(
                name = name,
                description = description,
                deadline = deadline,
                priority = priority,
                subtasks = subtasks
            )
        )
    }

    val uiState: StateFlow<TaskState> = _uiState.asStateFlow()

    private var initialCategoryId: String? = null

    init {
        viewModelScope.launch {
            initializeCategories()
            updateInitialCategoryId()
        }
    }

    private fun CoroutineScope.initializeCategories() {
        launch {
            getCategoriesUseCase()
                .onSuccess { categories ->
                    _uiState.update { it.copy(categories = categories) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(error = error.message) }
                }
        }
    }

    private fun CoroutineScope.updateInitialCategoryId() {
        launch {
            initialTask.category?.let { initialCategory ->
                val id = getTaskCategoryIdUseCase(initialCategory.name).getOrNull()
                initialCategoryId = id

                _uiState.update { it.copy(categoryId = id) }
            }
        }
    }

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.UpdateTaskDescription -> updateTaskDescription(event.description)

            is TaskEvent.UpdateTaskName -> updateTaskName(event.name)

            is TaskEvent.UpdateTaskDeadline -> updateTaskDeadline(
                event.date,
                event.hour,
                event.minute
            )

            is TaskEvent.UpdateTaskPriority -> updateTaskPriority(event.priority)

            is TaskEvent.UpdateTaskCategory -> updateTaskCategory(event.categoryId)

            is TaskEvent.RemoveSubtask -> removeSubtask(event.index)

            is TaskEvent.UpdateSubtask -> updateSubtask(event.index, event.subtaskName)

            TaskEvent.AddSubtask -> addSubtask()

            TaskEvent.DeleteTask -> deleteTask(taskId)

            TaskEvent.EditTask -> editTask()
        }
    }

    private fun deleteTask(taskId: String) {
        viewModelScope.launch {
            deleteTaskUseCase(taskId)
        }
    }

    private fun updateTaskDescription(description: String) {
        _uiState.update {
            it.copy(description = description)
        }
    }

    private fun updateTaskName(name: String) {
        _uiState.update {
            it.copy(name = name)
        }
    }

    private fun updateTaskDeadline(date: Long?, hour: Int, minute: Int) {
        _uiState.update {
            val instant = Instant.fromEpochMilliseconds(date ?: 0)

            val deadline: LocalDateTime = instant
                .plus(hour, unit = DateTimeUnit.HOUR)
                .plus(minute, unit = DateTimeUnit.MINUTE)
                .toLocalDateTime(TimeZone.UTC)

            it.copy(deadline = deadline)
        }
    }

    private fun updateTaskPriority(priority: Int?) {
        _uiState.update {
            it.copy(
                priority = priority.toTaskPriority()
            )
        }
    }

    private fun updateTaskCategory(categoryId: String?) {
        _uiState.update { it.copy(categoryId = categoryId) }
    }

    private fun addSubtask() {
        _uiState.update { it.copy(subtasks = it.subtasks + Subtask(name = "")) }
    }

    private fun editTask() {
        val (taskWasChanged, task) = isInitialTaskUpdated()

        if (taskWasChanged) {
            viewModelScope.launch {
                updateTaskUseCase(
                    task = task,
                    id = taskId,
                    categoryId = _uiState.value.categoryId
                )
            }
        }
    }

    private fun isInitialTaskUpdated(): Pair<Boolean, Task> {
        val stateTask = getCurrentStateTask()

        return (stateTask != initialTask || _uiState.value.categoryId != initialCategoryId) to stateTask
    }

    private fun getCurrentStateTask(): Task {
        return with(_uiState.value) {
            Task(
                name = name,
                description = description,
                priority = priority,
                category = initialTask.category,
                subtasks = subtasks.filter { it.name.isNotEmpty() },
                deadline = deadline ?: LocalDateTime.default,
                completed = initialTask.completed,
            )
        }
    }

    private fun removeSubtask(index: Int) {
        _uiState.update {
            val updatedSubtasks = it.subtasks.toMutableList()
            updatedSubtasks.removeAt(index)

            it.copy(subtasks = updatedSubtasks)
        }
    }

    private fun updateSubtask(index: Int, subtaskName: String) {
        _uiState.update {
            val updatedSubtasks = it.subtasks.toMutableList()
            updatedSubtasks[index] = Subtask(name = subtaskName)

            it.copy(subtasks = updatedSubtasks)
        }
    }
}
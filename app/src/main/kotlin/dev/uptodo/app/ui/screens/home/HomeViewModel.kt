package dev.uptodo.app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.uptodo.domain.model.toTaskPriority
import dev.uptodo.domain.usecase.CreateTaskCategoryUseCase
import dev.uptodo.domain.usecase.CreateTaskUseCase
import dev.uptodo.domain.usecase.DeleteTaskUseCase
import dev.uptodo.domain.usecase.GetCurrentDateTasksUseCase
import dev.uptodo.domain.usecase.GetTaskCategoriesUseCase
import dev.uptodo.domain.usecase.UpdateTaskCompleteStateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getCurrentDateTasksUseCase: GetCurrentDateTasksUseCase,
    private val getCategoriesUseCase: GetTaskCategoriesUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val updateTaskCompleteStateUseCase: UpdateTaskCompleteStateUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getCategoriesUseCase()
                .onSuccess { categories ->
                    _uiState.update { it.copy(categories = categories) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(error = error.message) }
                }

            getCurrentDateTasksUseCase()
                .catch { e ->
                    _uiState.update { state ->
                        state.copy(error = e.message)
                    }
                }
                .collectLatest { tasks ->
                    _uiState.update { state ->
                        val (todoTasks, completedTasks) = tasks.toList()
                            .partition { !it.second.completed }

                        state.copy(todoTasks = todoTasks, completedTasks = completedTasks)
                    }
                }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.UpdateSheetTaskTitle -> updateSheetTaskTitle(event.title)

            is HomeEvent.UpdateSheetTaskDescription -> updateSheetTaskDescription(event.description)

            is HomeEvent.UpdateSheetTaskDeadline -> updateSheetTaskDeadline(
                event.date,
                event.hour,
                event.minute
            )

            is HomeEvent.UpdateSheetTaskCategory -> updateSheetTaskCategory(event.categoryId)

            is HomeEvent.UpdateSheetTaskPriority -> updateSheetTaskPriority(event.priority)

            is HomeEvent.ToggleTaskCompleteState -> updateTaskCompleteState(event.taskId)

            is HomeEvent.DeleteTask -> deleteTask(event.taskId)

            HomeEvent.CreateTask -> createTask()
        }
    }

    private fun updateSheetTaskTitle(title: String) {
        _uiState.update { it.copy(sheetTaskTitle = title) }
    }

    private fun updateSheetTaskDescription(description: String) {
        _uiState.update { it.copy(sheetTaskDescription = description) }
    }

    private fun updateSheetTaskDeadline(date: Long?, hour: Int, minute: Int) {
        _uiState.update {
            val instant = Instant.fromEpochMilliseconds(date ?: 0)

            val deadline: LocalDateTime = instant
                .plus(hour, unit = DateTimeUnit.HOUR)
                .plus(minute, unit = DateTimeUnit.MINUTE)
                .toLocalDateTime(TimeZone.UTC)

            it.copy(sheetTaskDeadline = deadline)
        }
    }

    private fun updateSheetTaskCategory(categoryId: String) {
        _uiState.update { it.copy(sheetTaskCategoryId = categoryId) }
    }

    private fun updateSheetTaskPriority(priority: Int?) {
        _uiState.update {
            it.copy(sheetTaskPriority = priority.toTaskPriority())
        }
    }

    private fun updateTaskCompleteState(taskId: String) {
        viewModelScope.launch {
            updateTaskCompleteStateUseCase(taskId = taskId)
        }
    }

    private fun deleteTask(taskId: String) {
        viewModelScope.launch {
            deleteTaskUseCase(id = taskId)
        }
    }

    private fun createTask() {
        if (_uiState.value.sheetTaskTitle.isEmpty()) {
            _uiState.update { it.copy(error = "Title cannot be empty!") }
            return
        }

        val defaultDeadline = Clock.System.now()
            .toLocalDateTime(TimeZone.UTC)
            .date
            .atTime(LocalTime(23, 59))

        viewModelScope.launch {
            with(_uiState.value) {
                createTaskUseCase(
                    name = sheetTaskTitle,
                    description = sheetTaskDescription,
                    priority = sheetTaskPriority,
                    categoryId = sheetTaskCategoryId.ifEmpty { null },
                    deadline = sheetTaskDeadline ?: defaultDeadline,
                    subtasks = emptyList()
                )
            }
        }
    }
}
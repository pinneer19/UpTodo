package dev.uptodo.app.ui.screens.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.uptodo.app.R
import dev.uptodo.app.di.main.home.HomeScope
import dev.uptodo.app.ui.util.UiText
import dev.uptodo.app.ui.util.toUiText
import dev.uptodo.domain.usecase.DeleteTaskCategoryUseCase
import dev.uptodo.domain.usecase.DeleteTaskUseCase
import dev.uptodo.domain.usecase.GetCurrentDateTasksUseCase
import dev.uptodo.domain.usecase.UpdateTaskCompleteStateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HomeScope
class HomeViewModel @Inject constructor(
    private val getCurrentDateTasksUseCase: GetCurrentDateTasksUseCase,
    private val updateTaskCompleteStateUseCase: UpdateTaskCompleteStateUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val deleteTaskCategoryUseCase: DeleteTaskCategoryUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getCurrentDateTasksUseCase()
                .catch { e ->
                    _uiState.update { state ->
                        state.copy(error = e.message.toUiText())
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
            is HomeEvent.ToggleTaskCompleteState -> updateTaskCompleteState(taskId = event.taskId)

            is HomeEvent.DeleteTask -> deleteTask(taskId = event.taskId)

            is HomeEvent.DeleteTaskCategory -> deleteTaskCategory(categoryId = event.categoryId)

            is HomeEvent.UpdateError -> updateError(message = event.message)

            HomeEvent.ClearError -> updateError(message = null)

            HomeEvent.ShowBottomSheet -> updateBottomSheetVisibility(visible = true)

            HomeEvent.HideBottomSheet -> updateBottomSheetVisibility(visible = false)
        }
    }

    private fun updateTaskCompleteState(taskId: String) {
        viewModelScope.launch {
            updateTaskCompleteStateUseCase(taskId = taskId)
        }
    }

    private fun deleteTask(taskId: String) {
        viewModelScope.launch {
            val result = deleteTaskUseCase(id = taskId)

            if (result.isFailure) {
                _uiState.update {
                    it.copy(error = R.string.delete_task_failure.toUiText())
                }
            }
        }
    }

    private fun deleteTaskCategory(categoryId: String) {
        viewModelScope.launch {
            val result = deleteTaskCategoryUseCase(id = categoryId)

            if (result.isFailure) {
                _uiState.update {
                    it.copy(
                        error = R.string.delete_task_category_failure.toUiText()
                    )
                }
            }
        }
    }

    private fun updateError(message: UiText?) {
        _uiState.update { it.copy(error = message) }
    }

    private fun updateBottomSheetVisibility(visible: Boolean) {
        _uiState.update {
            it.copy(showBottomSheet = visible)
        }
    }
}
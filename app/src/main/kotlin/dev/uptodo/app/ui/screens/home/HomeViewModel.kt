package dev.uptodo.app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.uptodo.domain.model.Task
import dev.uptodo.domain.usecase.GetTasksUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val tasks: List<Task> = emptyList(),
    val error: String? = null
)

class HomeViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val tasksFlow: Flow<List<Task>> = getTasksUseCase()

            tasksFlow
                .catch { e ->
                    _uiState.update { state ->
                        state.copy(error = e.message)
                    }
                }
                .collectLatest { tasks ->
                    _uiState.update { state ->
                        state.copy(tasks = tasks)
                    }
                }
        }
    }
}
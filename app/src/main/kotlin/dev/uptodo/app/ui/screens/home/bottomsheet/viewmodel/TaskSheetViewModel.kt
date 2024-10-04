package dev.uptodo.app.ui.screens.home.bottomsheet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.uptodo.app.R
import dev.uptodo.app.di.main.home.HomeScope
import dev.uptodo.app.util.Constants
import dev.uptodo.app.util.voice.VoiceListener
import dev.uptodo.app.ui.util.toUiText
import dev.uptodo.domain.model.toTaskPriority
import dev.uptodo.domain.usecase.CreateTaskUseCase
import dev.uptodo.domain.usecase.DeleteTaskCategoryUseCase
import dev.uptodo.domain.usecase.GetTaskCategoriesFlowUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

@HomeScope
class TaskSheetViewModel @Inject constructor(
    private val createTaskUseCase: CreateTaskUseCase,
    private val getCategoriesFlowUseCase: GetTaskCategoriesFlowUseCase,
    private val deleteTaskCategoryUseCase: DeleteTaskCategoryUseCase,
    private val voiceListener: VoiceListener
) : ViewModel() {

    private var _uiState = MutableStateFlow(TaskSheetState())
    val uiState: StateFlow<TaskSheetState> = _uiState

    init {
        viewModelScope.launch {
            launch {
                getCategoriesFlowUseCase().collect { categories ->
                    _uiState.update {
                        it.copy(categories = categories)
                    }
                }
            }

            launch {
                voiceListener.state.collect { voiceState ->
                    _uiState.update {
                        println(voiceState.spokenText)
                        it.copy(
                            title = voiceState.spokenText,
                            speaking = voiceState.speaking,
                            error = voiceState.error,
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: TaskSheetEvent) {
        when (event) {
            is TaskSheetEvent.UpdateTitle -> updateTitle(event.title)

            is TaskSheetEvent.UpdateDescription -> updateDescription(event.description)

            is TaskSheetEvent.UpdateDeadline -> updateDeadline(
                event.date,
                event.hour,
                event.minute
            )

            is TaskSheetEvent.UpdateCategory -> updateCategory(event.categoryId)

            is TaskSheetEvent.UpdatePriority -> updatePriority(event.priority)

            is TaskSheetEvent.DeleteTaskCategory -> deleteTaskCategory(event.categoryId)

            is TaskSheetEvent.CreateTask -> createTask(event.onCreateTaskReminder)

            is TaskSheetEvent.UpdateError -> updateError(event.message)

            is TaskSheetEvent.RecordVoice -> startVoiceRecording(event.languageCode)

            TaskSheetEvent.StopRecording -> stopVoiceRecording()
        }
    }

    private fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    private fun updateDescription(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    private fun updateDeadline(date: Long?, hour: Int, minute: Int) {
        _uiState.update {
            val instantAtSystemTimeZone = Instant.fromEpochMilliseconds(date ?: 0)
                .toLocalDateTime(TimeZone.UTC)
                .toInstant(TimeZone.currentSystemDefault())

            val deadline: LocalDateTime = instantAtSystemTimeZone
                .plus(value = hour, unit = DateTimeUnit.HOUR)
                .plus(value = minute, unit = DateTimeUnit.MINUTE)
                .toLocalDateTime(timeZone = TimeZone.currentSystemDefault())

            it.copy(deadline = deadline)
        }
    }

    private fun updateCategory(categoryId: String?) {
        _uiState.update { it.copy(categoryId = categoryId) }
    }

    private fun updatePriority(priority: Int?) {
        _uiState.update {
            it.copy(priority = priority.toTaskPriority())
        }
    }

    private fun deleteTaskCategory(categoryId: String) {
        viewModelScope.launch {
            deleteTaskCategoryUseCase(categoryId)
        }
    }

    private fun createTask(
        onScheduleTaskReminder: (String, String, LocalDateTime) -> Unit
    ) {
        if (_uiState.value.title.isEmpty()) {
            _uiState.update {
                it.copy(error = R.string.empty_title.toUiText())
            }

            return
        }

        viewModelScope.launch {
            with(_uiState.value) {
                val deadline = deadline ?: Constants.DefaultDeadline

                val result = createTaskUseCase(
                    name = title,
                    description = description,
                    priority = priority,
                    categoryId = categoryId,
                    deadline = deadline,
                    subtasks = emptyList()
                )

                if (result.isSuccess) {
                    onScheduleTaskReminder(result.getOrThrow(), title, deadline)
                } else {
                    _uiState.update {
                        it.copy(
                            error = R.string.create_task_failure.toUiText()
                        )
                    }
                }
            }
        }
    }

    private fun updateError(message: String) {
        _uiState.update { it.copy(error = message.toUiText()) }
    }

    private fun startVoiceRecording(languageCode: String) {
        voiceListener.startListening(languageCode)
    }

    private fun stopVoiceRecording() {
        voiceListener.stopListening()
    }
}
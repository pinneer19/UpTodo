package dev.uptodo.app.ui.screens.category.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.uptodo.app.R
import dev.uptodo.app.di.main.category.CategoryScope
import dev.uptodo.app.ui.screens.category.dialog.icon.IconItem
import dev.uptodo.app.ui.util.decodeMaterialIcon
import dev.uptodo.app.ui.util.fromHex
import dev.uptodo.app.ui.util.toHexString
import dev.uptodo.app.ui.util.toUiText
import dev.uptodo.app.util.Constants.DEFAULT_ICON_PACKAGE
import dev.uptodo.domain.model.TaskCategory
import dev.uptodo.domain.usecase.CreateTaskCategoryUseCase
import dev.uptodo.domain.usecase.DeleteTaskCategoryUseCase
import dev.uptodo.domain.usecase.GetIconNamesUseCase
import dev.uptodo.domain.usecase.UpdateTaskCategoryUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@CategoryScope
class CategoryViewModel @Inject constructor(
    private val getIconNamesUseCase: GetIconNamesUseCase,
    private val createTaskCategoryUseCase: CreateTaskCategoryUseCase,
    private val updateTaskCategoryUseCase: UpdateTaskCategoryUseCase,
    private val deleteTaskCategoryUseCase: DeleteTaskCategoryUseCase,
    val id: String?,
    val category: TaskCategory?,
) : ViewModel() {

    private val _uiState = category?.let {
        MutableStateFlow(
            CategoryState(
                name = it.name,
                iconUri = it.iconUri,
                color = Color.fromHex(it.iconTint),
                editMode = id != null
            )
        )
    } ?: MutableStateFlow(CategoryState())

    val uiState: StateFlow<CategoryState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        updateIconPickerSearchInput(_uiState.value.iconPickerSearchInput)
    }

    fun onEvent(event: CategoryEvent) {
        when (event) {
            is CategoryEvent.PickCategoryIcon -> updateCategoryIcon(event.icon)

            is CategoryEvent.UpdateCategoryColor -> updateCategoryColor(event.color)

            is CategoryEvent.UpdateCategoryName -> updateCategoryName(event.name)

            is CategoryEvent.UpdateIconPickerSearchInput -> updateIconPickerSearchInput(event.searchInput)

            CategoryEvent.SaveCategory -> saveCategory()

            CategoryEvent.DeleteCategory -> deleteCategory()

            CategoryEvent.CreateCategory -> createCategory()
        }
    }

    private fun updateCategoryIcon(icon: String) {
        _uiState.update { it.copy(iconUri = "$DEFAULT_ICON_PACKAGE.$icon") }
    }

    private fun updateCategoryColor(color: Color?) {
        _uiState.update { it.copy(color = color) }
    }

    private fun updateCategoryName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    private fun updateIconPickerSearchInput(searchInput: String) {
        _uiState.update { it.copy(iconPickerSearchInput = searchInput) }

        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(400)

            val icons = getIconNamesUseCase()
                .filter { it.contains(searchInput, ignoreCase = true) }
                .take(50)
                .map { parseIconItem(it) }
                .chunked(3)

            _uiState.update { it.copy(iconPickerItems = icons) }
        }
    }

    private fun parseIconItem(line: String): IconItem {
        val (id, name) = line.split(",")
        val iconName = "$DEFAULT_ICON_PACKAGE.$id"

        val image = decodeMaterialIcon(name = iconName)

        return IconItem(id, name, image)
    }

    private fun saveCategory() {
        with(_uiState.value) {
            if (color == null) {
                _uiState.update {
                    it.copy(error = R.string.unspecified_color.toUiText())
                }
            }

            viewModelScope.launch {
                updateTaskCategoryUseCase(
                    id = requireNotNull(id),
                    category = TaskCategory(
                        name = name,
                        iconUri = iconUri,
                        iconTint = color.toHexString()
                    )
                )
            }
        }
    }

    private fun deleteCategory() {
        viewModelScope.launch {
            deleteTaskCategoryUseCase(id = requireNotNull(id))
        }
    }

    private fun createCategory() {
        viewModelScope.launch {
            with(_uiState.value) {
                createTaskCategoryUseCase(
                    categoryName = name,
                    iconUri = iconUri,
                    iconTint = color.toHexString()
                )
            }
        }
    }
}
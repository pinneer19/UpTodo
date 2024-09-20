package dev.uptodo.app.ui.screens.category

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.uptodo.app.ui.util.decodeMaterialIcon
import dev.uptodo.app.ui.util.toHexString
import dev.uptodo.domain.usecase.CreateTaskCategoryUseCase
import dev.uptodo.domain.usecase.GetIconNamesUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryViewModel @Inject constructor(
    private val getIconNamesUseCase: GetIconNamesUseCase,
    private val createTaskCategoryUseCase: CreateTaskCategoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryState())
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
        val image = decodeMaterialIcon(name = "$DEFAULT_ICON_PACKAGE.$id")

        return IconItem(id, name, image)
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

    companion object {
        private const val DEFAULT_ICON_PACKAGE = "Icons.Default"
    }
}
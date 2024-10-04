package dev.uptodo.app.ui.screens.home.viewmodel

import dev.uptodo.app.ui.util.UiText
import dev.uptodo.domain.model.Task

data class HomeState(
    val todoTasks: List<Pair<String, Task>> = emptyList(),
    val completedTasks: List<Pair<String, Task>> = emptyList(),
    var showBottomSheet: Boolean = false,
    val error: UiText? = null
)
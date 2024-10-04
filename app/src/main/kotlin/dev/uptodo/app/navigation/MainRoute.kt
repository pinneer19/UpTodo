package dev.uptodo.app.navigation

import dev.uptodo.domain.model.Task
import dev.uptodo.domain.model.TaskCategory
import kotlinx.serialization.Serializable

sealed class MainRoute : Route() {

    @Serializable
    data object Home : MainRoute()

    @Serializable
    data class Category(val id: String? = null, val category: TaskCategory? = null) : MainRoute()

    @Serializable
    data class TaskDetails(val id: String, val task: Task) : MainRoute()
}
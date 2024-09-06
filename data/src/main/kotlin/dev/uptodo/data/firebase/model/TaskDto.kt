package dev.uptodo.data.firebase.model

import dev.uptodo.domain.model.Subtask
import dev.uptodo.domain.model.Task
import dev.uptodo.domain.model.TaskCategory
import dev.uptodo.domain.model.TaskPriority
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
internal data class TaskDto(
    val id: String,
    val name: String,
    val description: String,
    val priority: TaskPriority,
    val categoryId: String?,
    val subtasks: List<Subtask>,
    val deadline: LocalDateTime,
    val completed: Boolean = false
) {
    val json = Json.encodeToString(this)

    fun toTask(category: TaskCategory) = Task(
        name, description, priority, category, subtasks, deadline, completed
    )
}
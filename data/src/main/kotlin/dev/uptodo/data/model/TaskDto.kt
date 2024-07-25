package dev.uptodo.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import dev.uptodo.domain.model.Subtask
import dev.uptodo.domain.model.Task
import dev.uptodo.domain.model.TaskCategory
import dev.uptodo.domain.model.TaskPriority
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
@Entity(foreignKeys = [
    ForeignKey(
        entity = TaskCategoryDto::class,
        parentColumns = ["id"],
        childColumns = ["categoryId"],
        onDelete = ForeignKey.CASCADE
    )
])
internal data class TaskDto(
    @PrimaryKey(autoGenerate = false) val id: String,
    val name: String,
    val description: String,
    val priority: TaskPriority,
    val categoryId: String,
    val subtasks: List<Subtask>,
    val deadline: LocalDateTime,
) {
    val json = Json.encodeToString(this)

    fun toTask(category: TaskCategory) = Task(
        name, description, priority, category, subtasks, deadline,
    )
}
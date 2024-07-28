package dev.uptodo.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import dev.uptodo.domain.model.Subtask
import dev.uptodo.domain.model.TaskPriority
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = TaskCategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val name: String,
    val description: String,
    val priority: TaskPriority,
    val categoryId: String,
    val subtasks: List<Subtask>,
    val deadline: LocalDateTime,
)
package dev.uptodo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.uptodo.domain.model.TaskCategory
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
@Entity
internal data class TaskCategoryDto(
    @PrimaryKey(autoGenerate = false) val id: String,
    val name: String,
    val iconUri: String,
    val iconTint: String
) {
    val json: String = Json.encodeToString(this)

    fun toTaskCategory() = TaskCategory(
        name, iconUri, iconTint
    )
}
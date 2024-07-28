package dev.uptodo.data.firebase.model

import dev.uptodo.domain.model.TaskCategory
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class TaskCategoryDto(
    val id: String,
    val name: String,
    val iconUri: String,
    val iconTint: String
) {
    val json: String = Json.encodeToString(this)

    fun toTaskCategory() = TaskCategory(
        name, iconUri, iconTint
    )
}
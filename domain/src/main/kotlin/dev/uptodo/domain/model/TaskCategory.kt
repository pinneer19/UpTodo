package dev.uptodo.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class TaskCategory(
    val name: String,
    val iconUri: String,
    val iconTint: String // hex
)
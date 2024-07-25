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

val TaskCategory.json: String
    get() = Json.encodeToString(this)

//enum class TaskCategory {
//    Grocery,
//    Work,
//    Sport,
//    Design,
//    University,
//    Social,
//    Music,
//    Health,
//    Movie,
//    Home
//}
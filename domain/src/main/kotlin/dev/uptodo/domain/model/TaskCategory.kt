package dev.uptodo.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TaskCategory(
    val name: String,
    val iconUri: String,
    val iconTint: String // hex
)

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
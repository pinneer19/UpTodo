package dev.uptodo.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class TaskPriority {
    ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, DEFAULT
}
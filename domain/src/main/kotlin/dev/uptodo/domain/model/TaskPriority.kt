package dev.uptodo.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class TaskPriority {
    ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, DEFAULT;

    fun toInt(): Int? {
        return if (this == DEFAULT) null
        else ordinal + 1
    }

    override fun toString(): String {
        return this.toInt()?.toString() ?: "Default"
    }
}

fun Int?.toTaskPriority() =
    this?.let {
        TaskPriority.entries.getOrNull(it - 1)
    } ?: TaskPriority.DEFAULT
package dev.uptodo.domain.model

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class Subtask(val name: String)
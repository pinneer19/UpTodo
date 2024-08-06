package dev.uptodo.data.local.util

import androidx.room.TypeConverter
import dev.uptodo.domain.model.Subtask
import dev.uptodo.domain.model.TaskPriority
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class TaskPriorityConverter {
    @TypeConverter
    fun fromTaskPriority(value: TaskPriority): String {
        return value.name
    }

    @TypeConverter
    fun toTaskPriority(value: String): TaskPriority {
        return TaskPriority.valueOf(value)
    }
}

class LocalDateTimeConverter {
    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toLocalDateTime(value: String): LocalDateTime {
        return Json.decodeFromString(value)
    }
}

class SubtaskListConverter {
    @TypeConverter
    fun fromSubtaskList(value: List<Subtask>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toSubtaskList(value: String): List<Subtask> {
        return Json.decodeFromString(value)
    }
}
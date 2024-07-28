package dev.uptodo.data.local.util

import androidx.room.TypeConverter
import dev.uptodo.domain.model.TaskPriority

class Converters {
    @TypeConverter
    fun fromTaskPriority(value: TaskPriority): String {
        return value.name
    }

    @TypeConverter
    fun toTaskPriority(value: String): TaskPriority {
        return TaskPriority.valueOf(value)
    }
}
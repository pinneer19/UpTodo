package dev.uptodo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.uptodo.data.local.dao.TaskCategoryDao
import dev.uptodo.data.local.dao.TaskDao
import dev.uptodo.data.local.entity.TaskCategoryEntity
import dev.uptodo.data.local.entity.TaskEntity
import dev.uptodo.data.local.util.LocalDateTimeConverter
import dev.uptodo.data.local.util.SubtaskListConverter
import dev.uptodo.data.local.util.TaskPriorityConverter

@Database(entities = [TaskEntity::class, TaskCategoryEntity::class], version = 1)
@TypeConverters(
    TaskPriorityConverter::class,
    LocalDateTimeConverter::class,
    SubtaskListConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao
    abstract val taskCategoryDao: TaskCategoryDao
}
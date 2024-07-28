package dev.uptodo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.uptodo.data.local.dao.TaskCategoryDao
import dev.uptodo.data.local.dao.TaskDao
import dev.uptodo.data.local.entity.TaskCategoryEntity
import dev.uptodo.data.local.entity.TaskEntity

@Database(entities = [TaskEntity::class, TaskCategoryEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract val taskDao: TaskDao
    abstract val taskCategoryDao: TaskCategoryDao
}
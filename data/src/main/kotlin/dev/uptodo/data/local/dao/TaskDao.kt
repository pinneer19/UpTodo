package dev.uptodo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import dev.uptodo.data.local.entity.TaskEntity
import dev.uptodo.data.local.entity.TaskWithCategory

@Dao
interface TaskDao {
    @Insert
    suspend fun createTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query("DELETE FROM tasks where id = :id")
    suspend fun deleteTaskById(id: String)

    @Transaction
    @Query("SELECT * FROM tasks")
    suspend fun getAllTasksWithCategories(): List<TaskWithCategory>
}
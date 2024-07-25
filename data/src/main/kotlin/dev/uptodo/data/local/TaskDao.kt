package dev.uptodo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import dev.uptodo.data.model.TaskDto
import dev.uptodo.data.model.TaskWithCategory

@Dao
internal interface TaskDao {
    @Insert
    suspend fun createTask(task: TaskDto)

    @Update
    suspend fun updateTask(task: TaskDto)

    @Query("DELETE FROM taskdto where id = :id")
    suspend fun deleteTaskById(id: String)

    @Transaction
    @Query("SELECT * FROM taskdto")
    suspend fun getAllTasksWithCategories(): List<TaskWithCategory>
}
package dev.uptodo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import dev.uptodo.data.local.entity.TaskEntity
import dev.uptodo.data.local.entity.TaskWithCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

@Dao
interface TaskDao {
    @Insert
    suspend fun createTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query("UPDATE tasks SET completed = NOT completed WHERE id = :taskId")
    suspend fun updateTaskCompleteState(taskId: String)

    @Query("DELETE FROM tasks where id = :id")
    suspend fun deleteTaskById(id: String)

    @Transaction
    @Query("SELECT * FROM tasks")
    suspend fun getAllTasksWithCategories(): List<TaskWithCategory>

    @Transaction
    @Query("SELECT * FROM tasks WHERE deadline BETWEEN :startOfDay AND :endOfDay")
    fun getTasksWithCategoriesByDate(
        startOfDay: LocalDateTime,
        endOfDay: LocalDateTime
    ): Flow<List<TaskWithCategory>>

    @Transaction
    @Query("SELECT COUNT(*) FROM tasks WHERE deadline BETWEEN :startOfDay AND :endOfDay")
    suspend fun getTasksWithCategoriesAmountByDate(
        startOfDay: LocalDateTime,
        endOfDay: LocalDateTime
    ): Int
}
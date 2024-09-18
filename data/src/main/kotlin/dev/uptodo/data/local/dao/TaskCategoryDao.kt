package dev.uptodo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.uptodo.data.local.entity.TaskCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskCategoryDao {
    @Insert
    suspend fun createTaskCategory(taskCategory: TaskCategoryEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun initializeTaskCategories(taskCategories: List<TaskCategoryEntity>)

    @Update
    suspend fun updateTaskCategory(taskCategory: TaskCategoryEntity)

    @Query("DELETE FROM categories where id = :id")
    suspend fun deleteTaskCategoryById(id: String)

    @Query("SELECT * FROM categories")
    suspend fun getAllTaskCategories(): List<TaskCategoryEntity>

    @Query("SELECT * FROM categories")
    fun getTaskCategoriesFlow(): Flow<List<TaskCategoryEntity>>

    @Query("SELECT id FROM categories WHERE name = :name")
    suspend fun getTaskCategoryId(name: String): String
}
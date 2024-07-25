package dev.uptodo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import dev.uptodo.data.model.TaskCategoryDto

@Dao
internal interface TaskCategoryDao {
    @Insert
    suspend fun createTaskCategory(taskCategory: TaskCategoryDto)

    @Update
    suspend fun updateTaskCategory(taskCategory: TaskCategoryDto)

    @Query("DELETE FROM taskcategorydto where id = :id")
    suspend fun deleteTaskCategoryById(id: String)

    @Query("SELECT * FROM taskcategorydto")
    suspend fun getAllTaskCategories(): List<TaskCategoryDto>
}
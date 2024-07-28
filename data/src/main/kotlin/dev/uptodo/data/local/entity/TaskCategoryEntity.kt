package dev.uptodo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class TaskCategoryEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    @ColumnInfo(name = "icon_uri")
    val iconUri: String,
    @ColumnInfo(name = "icon_tint")
    val iconTint: String
)
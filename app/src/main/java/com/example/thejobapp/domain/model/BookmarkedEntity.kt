package com.example.thejobapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val id: String,
    val title: String,
    val company: String,
    val location: String,
    val salary: String?,
    val type: String,
    val description: String
)
package com.example.thejobapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_jobs")
data class BookmarkedJobEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val location: String,
    val salary: String,
    val phone: String,
    val description: String
)
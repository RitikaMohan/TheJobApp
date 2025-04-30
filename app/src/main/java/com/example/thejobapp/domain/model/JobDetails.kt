package com.example.thejobapp.domain.model

data class JobDetails(
    val id: String,
    val title: String,
    val company: String,
    val location: String,
    val type: String,
    val description: String,
    val requirements: List<String>,
    val postedDate: String,
    val salary: String?,
    val applyUrl: String
)
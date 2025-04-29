package com.example.thejobapp.domain.model

data class Job(
    val id: Int,
    val title: String,
    val primary_details: PrimaryDetails?
)

data class PrimaryDetails(
    val Place: String?,
    val Salary: String?,
    val Job_Type: String?,
    val Experience: String?,
    val Fees_Charged: String?,
    val Qualification: String?
)
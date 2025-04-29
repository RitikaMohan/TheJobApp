package com.example.thejobapp.domain.model

data class JobDetails(
    val id: String,
    val title: String,
    val companyName: String,
    val city: String,
    val salary: String,
    val shift: String,
    val jobType: String,
    val content: String?,
    val contentV3: String?,
    val vacancies: String?,
    val gender: String?,
    val experience: String?,
    val education: String?,
    val address: String?,
    val hrPhoneNumber: String?,
    val hrWhatsAppNumber: String?,
    val creatives: List<String>?,
    val commission: String?,
    val fees: String?
)
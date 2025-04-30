package com.example.thejobapp.data.api

import com.example.thejobapp.domain.model.JobDetails
import com.google.gson.annotations.SerializedName

data class JobDetailsDto(
    val id: Int?,
    val title: String?,
    val company_name: String?,
    val custom_link: String?,
    val updated_on: String?,
    val other_details: String?,
    val salary_min: Int?,
    val primary_details: PrimaryDetailsDto?
)

data class PrimaryDetailsDto(
    @SerializedName("Place")
    val place: String?,
    @SerializedName("Job_Type")
    val jobType: String?
)

fun JobDetailsDto.toJobDetails(): JobDetails {
    return JobDetails(
        id = id?.toString() ?: "0",
        title = title ?: "No title",
        company = company_name ?: "Unknown company",
        location = primary_details?.place ?: "Unknown location",
        type = primary_details?.jobType ?: "Unknown",
        description = other_details ?: "No description",
        requirements = emptyList(),
        postedDate = updated_on ?: "Unknown date",
        salary = salary_min?.toString(),
        applyUrl = custom_link ?: ""
    )
}


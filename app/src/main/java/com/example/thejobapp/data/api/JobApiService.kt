package com.example.thejobapp.data.api

import com.example.thejobapp.domain.model.Job
import com.example.thejobapp.domain.model.JobDetails
import com.example.thejobapp.domain.model.JobResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JobApiService {
    @GET("common/jobs")
    suspend fun getJobs(@Query("page") page: Int): JobResponse

    @GET("common/jobs/{id}")
    suspend fun getJobDetails(@Path("id") id: String): JobDetailsDto
}
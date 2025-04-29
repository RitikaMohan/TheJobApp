package com.example.thejobapp.domain.repository

import androidx.paging.PagingData
import com.example.thejobapp.domain.model.Job
import com.example.thejobapp.domain.model.JobDetails
import kotlinx.coroutines.flow.Flow

interface JobRepository {
    fun getJobs(): Flow<PagingData<Job>>
    suspend fun getJobDetails(jobId: String): Result<JobDetails>
}

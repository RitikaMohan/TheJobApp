package com.example.thejobapp.domain.usecase

import com.example.thejobapp.domain.model.Job
import com.example.thejobapp.domain.model.JobDetails
import com.example.thejobapp.domain.repository.JobRepository

class GetJobDetailsUseCase(
    private val repository: JobRepository
) {
    suspend operator fun invoke(jobId: String): Result<JobDetails> {
        return repository.getJobDetails(jobId)
    }
}
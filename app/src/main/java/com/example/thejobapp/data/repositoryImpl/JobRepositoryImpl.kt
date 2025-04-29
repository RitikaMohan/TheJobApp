package com.example.thejobapp.data.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.thejobapp.data.api.JobApiService
import com.example.thejobapp.data.api.JobPagingSource
import com.example.thejobapp.data.api.RetrofitInstance
import com.example.thejobapp.data.api.RetrofitInstance.api
import com.example.thejobapp.domain.model.Job
import com.example.thejobapp.domain.model.JobDetails
import com.example.thejobapp.domain.repository.JobRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class JobRepositoryImpl : JobRepository {

    override fun getJobs(): Flow<PagingData<Job>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { JobPagingSource(api) } // use injected api here too
        ).flow
    }

    override suspend fun getJobDetails(jobId: String): Result<JobDetails> {
        return try {
            val response = api.getJobDetails(jobId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to load job details"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

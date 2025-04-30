package com.example.thejobapp.presentation.jobDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thejobapp.data.api.JobApiService
import com.example.thejobapp.data.api.RetrofitInstance
import com.example.thejobapp.data.repositoryImpl.JobRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.thejobapp.domain.usecase.GetJobDetailsUseCase

class JobDetailsViewModelFactory(
    private val jobId: String
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Step 1: Create Retrofit instance
        val api = Retrofit.Builder()
            .baseUrl("https://testapi.getlokalapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JobApiService::class.java)

        // Step 2: Build repository and use case
        val repository = JobRepositoryImpl(api)
        val useCase = GetJobDetailsUseCase(repository)

        // Step 3: Return the ViewModel
        return JobDetailsViewModel(useCase, jobId) as T
    }
}

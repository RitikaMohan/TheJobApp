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
    private val useCase: GetJobDetailsUseCase,
    private val jobId: String
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(JobDetailsViewModel::class.java)) {
            JobDetailsViewModel(useCase, jobId) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
        }
    }
}
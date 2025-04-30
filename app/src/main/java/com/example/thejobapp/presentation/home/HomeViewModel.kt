package com.example.thejobapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.thejobapp.data.api.JobApiService
import com.example.thejobapp.data.repositoryImpl.JobRepositoryImpl
import com.example.thejobapp.domain.model.Job
import com.example.thejobapp.domain.repository.JobRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class HomeViewModel : ViewModel() {

    private val apiService: JobApiService = Retrofit.Builder()
        .baseUrl("https://testapi.getlokalapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(JobApiService::class.java)

    private val repository: JobRepository = JobRepositoryImpl(apiService)

    val jobList: Flow<PagingData<Job>> = repository.getJobs().cachedIn(viewModelScope)
}
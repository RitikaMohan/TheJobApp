package com.example.thejobapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.thejobapp.data.api.JobApiService
import com.example.thejobapp.data.repositoryImpl.JobRepositoryImpl
import com.example.thejobapp.domain.model.Job
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeViewModel : ViewModel() {
    val repository = JobRepositoryImpl()

    val jobList: Flow<PagingData<Job>> = repository.getJobs().cachedIn(viewModelScope)
}
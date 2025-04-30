package com.example.thejobapp.presentation.jobDetails

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thejobapp.domain.model.JobDetails
import com.example.thejobapp.domain.usecase.GetJobDetailsUseCase
import kotlinx.coroutines.launch

class JobDetailsViewModel(
    private val getJobDetailsUseCase: GetJobDetailsUseCase,
    private val jobId: String
) : ViewModel() {

    var uiState by mutableStateOf<JobDetails?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            val result = getJobDetailsUseCase(jobId)
            Log.d("JobDetails", "Fetching details for jobId=$jobId")

            result.onSuccess { job ->
                uiState = job
            }.onFailure { e ->
                errorMessage = e.localizedMessage ?: "Unexpected error"
            }

            isLoading = false
        }
    }
}


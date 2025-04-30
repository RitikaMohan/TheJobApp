package com.example.thejobapp.presentation.jobDetails

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thejobapp.data.api.JobApiService
import com.example.thejobapp.data.repositoryImpl.JobRepositoryImpl
import com.example.thejobapp.domain.model.BookmarkEntity
import com.example.thejobapp.domain.model.JobDetails
import com.example.thejobapp.domain.usecase.GetJobDetailsUseCase
import com.example.thejobapp.presentation.bookmark.BookmarkViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@SuppressLint("ImplicitSamInstance")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailsScreen(
    jobId: String,
    bookmarkViewModel: BookmarkViewModel // Pass the BookmarkViewModel for bookmark management
) {
    Log.d("JobDetailsScreen", "Navigated to JobDetailsScreen with jobId=$jobId")

    // OkHttp and API setup
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
    val api = Retrofit.Builder()
        .baseUrl("https://testapi.getlokalapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(JobApiService::class.java)

    // Repository, UseCase, and ViewModel setup
    val repository = JobRepositoryImpl(api)
    val useCase = GetJobDetailsUseCase(repository)
    val viewModel: JobDetailsViewModel = viewModel(
        factory = JobDetailsViewModelFactory(useCase, jobId)
    )

    // State Observers
    val jobDetails = viewModel.uiState
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    // Bookmark State
    val isBookmarked = remember { mutableStateOf(false) }
    LaunchedEffect(jobId) {
        isBookmarked.value = bookmarkViewModel.isBookmarked(jobId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Job Details") },
                actions = {
                    IconButton(onClick = {
                        if (isBookmarked.value) {
                            jobDetails?.let {
                                bookmarkViewModel.removeBookmark(
                                    BookmarkEntity(
                                        id = it.id,
                                        title = it.title,
                                        company = it.company,
                                        location = it.location,
                                        salary = null, // Update this if salary exists
                                        type = "", // Add type if applicable
                                        description = it.description
                                    )
                                )
                            }
                        } else {
                            jobDetails?.let {
                                bookmarkViewModel.addBookmark(
                                    BookmarkEntity(
                                        id = it.id,
                                        title = it.title,
                                        company = it.company,
                                        location = it.location,
                                        salary = null, // Update this if salary exists
                                        type = "", // Add type if applicable
                                        description = it.description
                                    )
                                )
                            }
                        }
                        isBookmarked.value = !isBookmarked.value
                    }) {
                        Icon(
                            imageVector = if (isBookmarked.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (isBookmarked.value) "Remove Bookmark" else "Add Bookmark"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                errorMessage != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Error: $errorMessage", color = Color.Red)
                    }
                }

                jobDetails != null -> {
                    JobDetailsContent(details = jobDetails)
                }
            }
        }
    }
}

@Composable
fun JobDetailsContent(details: JobDetails) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(
                text = details.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = details.company,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = details.location,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Description", fontWeight = FontWeight.Bold)
            Text(
                text = details.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Requirements", fontWeight = FontWeight.Bold)
            details.requirements.forEach {
                Text("• $it", style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))
            ApplyButton(applyUrl = details.applyUrl)
        }
    }
}

@Composable
fun ApplyButton(applyUrl: String) {
    val context = LocalContext.current
    Button(
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, applyUrl.toUri())
            context.startActivity(intent)
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Apply Now")
    }
}
package com.example.thejobapp.presentation.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.thejobapp.domain.model.BookmarkEntity
import com.example.thejobapp.domain.model.Job
import com.example.thejobapp.presentation.bookmark.BookmarkViewModel

@SuppressLint("ImplicitSamInstance")
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel(), navController: NavController, bookmarkViewModel: BookmarkViewModel) {
    val jobs = viewModel.jobList.collectAsLazyPagingItems()
    val bookmarkedJobs by bookmarkViewModel.bookmarks.observeAsState(emptyList()) // Observe bookmarks state

    when {
        jobs.loadState.refresh is LoadState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        jobs.loadState.refresh is LoadState.Error -> {
            val e = jobs.loadState.refresh as LoadState.Error
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${e.error.localizedMessage}")
            }
        }
        jobs.itemCount == 0 -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No jobs found.")
            }
        }
        else -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(
                    count = jobs.itemCount,
                    key = jobs.itemKey { it?.id ?: "unknown" }, // Handle nullability for job.id
                    contentType = jobs.itemContentType { "job" }
                ) { index ->
                    val job = jobs[index]
                    val isBookmarked = bookmarkedJobs.any { it.id == job?.id }
                    if (job != null) {
                        JobCard(
                            job = job,
                            navController = navController,
                            isBookmarked = isBookmarked, // Handle nullability for job.id
                            onBookmarkClick = { bookmarkedJob ->
                                    if (isBookmarked) {
                                        bookmarkViewModel.removeBookmark(
                                            BookmarkEntity(
                                                id = bookmarkedJob.id,
                                                title = bookmarkedJob.title,
                                                company = bookmarkedJob.primary_details?.Place
                                                    ?: "",
                                                location = bookmarkedJob.primary_details?.Place
                                                    ?: "",
                                                salary = bookmarkedJob.primary_details?.Salary,
                                                type = "", // Add job type if available
                                                description = "" // Add job description if available
                                            )
                                        )
                                    } else {
                                        bookmarkViewModel.addBookmark(
                                            BookmarkEntity(
                                                id = bookmarkedJob.id,
                                                title = bookmarkedJob.title,
                                                company = bookmarkedJob.primary_details?.Place
                                                    ?: "",
                                                location = bookmarkedJob.primary_details?.Place
                                                    ?: "",
                                                salary = bookmarkedJob.primary_details?.Salary,
                                                type = "", // Add job type if available
                                                description = "" // Add job description if available
                                            )
                                        )
                                    }
                            }
                        )
                    } else {
                        Log.e("HomeScreen", "Job object is null at index $index.")
                    }
                }
            }
        }
    }
}

@Composable
fun JobCard(
    job: Job,
    navController: NavController,
    isBookmarked: Boolean, // Pass bookmark state
    onBookmarkClick: (Job) -> Unit // Callback for bookmark click
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                if (!job.id.isNullOrEmpty()) { // Ensure job.id is not null or empty
                    navController.navigate("job_details/${job.id}")
                } else {
                    Log.e("JobCard", "Job ID is null or empty. Navigation skipped.")
                }
            },
        elevation = CardDefaults.cardElevation()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = job.title ?: "No Title", // Provide default value if job.title is null
                    style = MaterialTheme.typography.titleMedium
                )
                Text(text = "Location: ${job.primary_details?.Place ?: "N/A"}")
                Text(text = "Salary: ${job.primary_details?.Salary ?: "Not Disclosed"}")
                Text(text = "Phone: Not Available") // Or show another value if exists
            }
            IconButton(onClick = { onBookmarkClick(job) }) {
                Icon(
                    imageVector = if (isBookmarked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (isBookmarked) "Remove Bookmark" else "Add Bookmark"
                )
            }
        }
    }
}
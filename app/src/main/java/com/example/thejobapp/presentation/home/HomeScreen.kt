package com.example.thejobapp.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.thejobapp.domain.model.Job

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel(), navController: NavController) {
    val jobs = viewModel.jobList.collectAsLazyPagingItems()

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
            LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                items(
                    count = jobs.itemCount,
                    key = jobs.itemKey { it.id },
                    contentType = jobs.itemContentType { "job" }
                ) { index ->
                    val job = jobs[index]
                    if (job != null) {
                        JobCard(job, navController = navController)
                    }
                }

                item {
                    if (jobs.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun JobCard(job: Job, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                navController.navigate("job_details/${job.id}")
            },
        elevation = CardDefaults.cardElevation()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = job.title ?: "No Title", style = MaterialTheme.typography.titleMedium)
            Text(text = "Location: ${job.primary_details?.Place ?: "N/A"}")
            Text(text = "Salary: ${job.primary_details?.Salary ?: "Not Disclosed"}")
            Text(text = "Phone: Not Available") // Or show another value if exists
        }
    }
}

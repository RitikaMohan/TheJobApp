package com.example.thejobapp.presentation.bookmark

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.thejobapp.domain.model.JobDetails


@Composable
fun BookmarkScreen(navController: NavController,viewModel: BookmarkViewModel, onJobClick: (JobDetails) -> Unit) {
    val bookmarks by viewModel.bookmarks.observeAsState(emptyList())

    LazyColumn {
        items(bookmarks) { bookmark ->
            JobItem(
                job = JobDetails( // Map BookmarkEntity to JobDetails
                    id = bookmark.id,
                    title = bookmark.title,
                    company = bookmark.company,
                    location = bookmark.location,
                    type = bookmark.type,
                    description = bookmark.description,
                    requirements = emptyList(),
                    postedDate = "",
                    salary = bookmark.salary,
                    applyUrl = ""
                ),
                navController = navController,
                isBookmarked = true,
                onBookmarkClick = { viewModel.removeBookmark(bookmark) }
            )
        }
    }
}

@Composable
fun JobItem(
    job: JobDetails,
    navController: NavController,
    isBookmarked: Boolean,
    onBookmarkClick: (JobDetails) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()
        .padding(vertical = 4.dp)
        .clickable {
            if (!job.id.isNullOrEmpty()) { // Ensure job.id is not null or empty
                navController.navigate("job_details/${job.id}")
            } else {
                Log.e("JobCard", "Job ID is null or empty. Navigation skipped.")
            }
        },
        elevation = CardDefaults.cardElevation()) {
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
                Text(text = "Location: ${job.location ?: "N/A"}")
                Text(text = "Salary: ${job.salary ?: "Not Disclosed"}")
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
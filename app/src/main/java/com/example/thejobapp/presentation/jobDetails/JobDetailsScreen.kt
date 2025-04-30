package com.example.thejobapp.presentation.jobDetails

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thejobapp.domain.model.JobDetails

@Composable
fun JobDetailsScreen(jobId: String) {
    val viewModel: JobDetailsViewModel = viewModel(
        factory = JobDetailsViewModelFactory(jobId)
    )

    val jobDetails = viewModel.uiState
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

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
            JobDetailsContent(jobDetails)
        }
    }
}

@Composable
fun JobDetailsContent(details: JobDetails) {
    val context = LocalContext.current
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            Text(text = details.title, style = MaterialTheme.typography.titleLarge)
            Text(text = details.company)
            Text(text = details.location)
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Description", fontWeight = FontWeight.Bold)
            Text(text = details.description)

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Requirements", fontWeight = FontWeight.Bold)
            details.requirements.forEach {
                Text("• $it")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, details.applyUrl.toUri())
                context.startActivity(intent)
            }) {
                Text("Apply Now")
            }
        }
    }
}

package com.example.thejobapp.presentation.navigation


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.thejobapp.presentation.bookmark.BookmarkScreen
import com.example.thejobapp.presentation.bookmark.BookmarkViewModel
import com.example.thejobapp.presentation.home.HomeScreen
import com.example.thejobapp.presentation.jobDetails.JobDetailsScreen

@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    val context = LocalContext.current

    // Initialize the BookmarkViewModel here
    val bookmarkViewModel = remember { BookmarkViewModel(context) }

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                navController = navController, // Pass navController here
                bookmarkViewModel = bookmarkViewModel
            )
        }


        composable(
            route = "job_details/{jobId}",
            arguments = listOf(navArgument("jobId") { type = NavType.StringType })
        ) { backStackEntry ->
            val jobId = backStackEntry.arguments?.getString("jobId") ?: ""
            JobDetailsScreen(
                jobId = jobId,
                bookmarkViewModel = bookmarkViewModel // Pass the BookmarkViewModel here
            )
        }

        composable("bookmarks") {
            BookmarkScreen(
                navController= navController,
                viewModel = bookmarkViewModel,
                onJobClick = { jobDetails ->
                    navController.navigate("job_details/${jobDetails.id}")
                }
            )
        }
    }
}
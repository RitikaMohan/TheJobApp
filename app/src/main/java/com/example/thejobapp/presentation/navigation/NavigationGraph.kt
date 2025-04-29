package com.example.thejobapp.presentation.navigation


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.thejobapp.presentation.bookmark.BookmarkScreen
import com.example.thejobapp.presentation.home.HomeScreen
//import com.example.thejobapp.presentation.jobdetails.JobDetailsScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Bookmarks.route) {
            BookmarkScreen()
        }
//        composable(
//            route = "job_details/{jobId}",
//            arguments = listOf(navArgument("jobId") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val jobId = backStackEntry.arguments?.getString("jobId") ?: ""
//            JobDetailsScreen(jobId = jobId)
//        }
    }
}

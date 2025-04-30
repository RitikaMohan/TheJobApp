package com.example.thejobapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.example.thejobapp.presentation.MainScreen
import com.example.thejobapp.ui.theme.TheJobAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        enableEdgeToEdge()
        setContent {
            TheJobAppTheme {
                MainScreen()
            }
        }
    }
}

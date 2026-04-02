package com.example.test_courses_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.test_courses_app.core.ui.CoursesAppTheme
import com.example.test_courses_app.screens.main.AppNavigation

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CoursesAppTheme {
                Surface {
                    val navController = rememberNavController()
                    AppNavigation(navController)
                }
            }
        }
    }
}
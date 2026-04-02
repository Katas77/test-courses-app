package com.example.test_courses_app.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.example.test_courses_app.screens.account.AccountScreen
import com.example.test_courses_app.screens.auth.LoginScreen
import com.example.test_courses_app.screens.favorite.FavoriteScreen

@Composable
fun AppNavigation(navController: androidx.navigation.NavController) {
    var isLoggedIn by remember { mutableStateOf(false) }

    if (!isLoggedIn) {
        LoginScreen(onLoginSuccess = { isLoggedIn = true })
    } else {
        MainScreenWrapper(onLogout = { isLoggedIn = false })
    }
}

@Composable
fun MainScreenWrapper(onLogout: () -> Unit) {
    val navController = rememberNavController()
    var selectedTab by remember { mutableStateOf("main") }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Text("🏠") },
                    label = { Text("Главная") },
                    selected = selectedTab == "main",
                    onClick = { 
                        selectedTab = "main"
                        navController.navigate("main") {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Text("❤️") },
                    label = { Text("Избранное") },
                    selected = selectedTab == "favorite",
                    onClick = { 
                        selectedTab = "favorite"
                        navController.navigate("favorite") {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Text("👤") },
                    label = { Text("Аккаунт") },
                    selected = selectedTab == "account",
                    onClick = { 
                        selectedTab = "account"
                        navController.navigate("account") {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (selectedTab) {
                "main" -> CoursesScreen()
                "favorite" -> FavoriteScreen()
                "account" -> AccountScreen(onLogout)
            }
        }
    }
}
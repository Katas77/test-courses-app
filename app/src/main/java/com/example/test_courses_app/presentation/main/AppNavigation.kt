package com.example.test_courses_app.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.test_courses_app.presentation.account.AccountScreen
import com.example.test_courses_app.presentation.auth.LoginScreen
import com.example.test_courses_app.presentation.favorite.FavoriteScreen

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
    val rootNavController = rememberNavController()
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
                        rootNavController.navigate("main") {
                            popUpTo(rootNavController.graph.findStartDestination().id)
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
                        rootNavController.navigate("favorite") {
                            popUpTo(rootNavController.graph.findStartDestination().id)
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
                        rootNavController.navigate("account") {
                            popUpTo(rootNavController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            NavHost(navController = rootNavController, startDestination = "main") {
                composable("main") { CoursesScreen() }
                composable("favorite") { FavoriteScreen() }
                composable("account") { AccountScreen(onLogout) }
            }
        }
    }
}
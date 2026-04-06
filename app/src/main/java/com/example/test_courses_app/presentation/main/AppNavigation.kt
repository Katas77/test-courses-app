package com.example.test_courses_app.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.test_courses_app.presentation.account.AccountScreen
import com.example.test_courses_app.presentation.auth.LoginScreen
import com.example.test_courses_app.presentation.favorite.FavoriteScreen

@Composable
fun AppNavigation() {
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
    var selectedTab by remember { mutableIntStateOf(0) }

    val navigationItems = listOf(
        NavigationItem("Главная", Icons.Outlined.Home, Icons.Filled.Home, "0"),
        NavigationItem("Избранное", Icons.Outlined.BookmarkBorder, Icons.Filled.BookmarkBorder, "1"),
        NavigationItem("Аккаунт", Icons.Outlined.AccountCircle, Icons.Filled.AccountCircle, "2")
    )

    Scaffold(
        containerColor = Color(0xFF1C1C1E),
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF1C1C1E),
                tonalElevation = 0.dp
            ) {
                navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = {
                            selectedTab = index
                            rootNavController.navigate(item.route) {
                                popUpTo(rootNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (selectedTab == index) item.filledIcon else item.outlinedIcon,
                                contentDescription = item.label,
                                tint = if (selectedTab == index) Color(0xFF4CAF50) else Color.Gray
                            )
                        },
                        label = {
                            Text(
                                text = item.label,
                                color = if (selectedTab == index) Color(0xFF4CAF50) else Color.Gray
                            )
                        }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = rootNavController,
            startDestination = "0",
            modifier = Modifier.padding(padding)
        ) {
            composable("0") { CoursesScreen() }
            composable("1") { FavoriteScreen() }
            composable("2") { AccountScreen(onLogout) }
        }
    }
}

data class NavigationItem(
    val label: String,
    val outlinedIcon: ImageVector,
    val filledIcon: ImageVector,
    val route: String
)


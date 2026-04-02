package com.example.test_courses_app.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*  // LaunchedEffect, remember, mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.test_courses_app.core.network.ApiClient
import com.example.test_courses_app.domain.Course
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CoursesScreen() {
    var courses by remember { mutableStateOf<List<Course>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var sortByDate by remember { mutableStateOf(false) }
    var originalCourses by remember { mutableStateOf<List<Course>>(emptyList()) }

    // 🔹 Триггер для перезагрузки
    var refreshTrigger by remember { mutableStateOf(0) }

    // 🔹 CoroutineScope для onClick
    val scope = rememberCoroutineScope()

    // ✅ Загрузка при первом запуске И при изменении refreshTrigger
    LaunchedEffect(refreshTrigger) {
        loadCourses(
            onSuccess = { list ->
                originalCourses = list
                courses = list
                isLoading = false
            },
            onError = { error ->
                errorMessage = error
                isLoading = false
            }
        )
    }

    Column(modifier = Modifier.padding(16.dp)) {
        // Поиск (заглушка)
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Поиск") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Сортировка
        TextButton(onClick = {
            sortByDate = !sortByDate
            courses = if (sortByDate) {
                originalCourses.sortedByDescending { it.publishDateMillis() }
            } else {
                originalCourses
            }
        }) {
            Text("🔽 Сортировка: ${if (sortByDate) "по дате" else "по умолчанию"}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Состояния
        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            errorMessage != null -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("❌ $errorMessage", color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))

                    // ✅ Исправлено: используем scope.launch вместо LaunchedEffect
                    Button(onClick = {
                        isLoading = true
                        errorMessage = null
                        scope.launch {  // ← Обычная корутина, а не Composable!
                            loadCourses(
                                onSuccess = { list ->
                                    originalCourses = list
                                    courses = list
                                    isLoading = false
                                },
                                onError = { error ->
                                    errorMessage = error
                                    isLoading = false
                                }
                            )
                        }
                    }) {
                        Text("Повторить")
                    }
                }
            }
            courses.isEmpty() -> {
                Text("Нет курсов", modifier = Modifier.padding(16.dp))
            }
            else -> {
                courses.forEach { course ->
                    CourseCard(course = course, onFavoriteToggle = {})
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

// 🔹 Вынесенная функция загрузки
private suspend fun loadCourses(
    onSuccess: (List<Course>) -> Unit,
    onError: (String) -> Unit
) {
    withContext(Dispatchers.IO) {
        try {
            val api = ApiClient.create()
            val response = api.getCourses()
            val domainCourses = response.courses.map { it.toDomain() }
            withContext(Dispatchers.Main) {
                onSuccess(domainCourses)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                onError(e.message ?: "Ошибка сети")
            }
        }
    }
}
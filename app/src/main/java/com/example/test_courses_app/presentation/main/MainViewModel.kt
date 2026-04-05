package com.example.test_courses_app.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_courses_app.domain.model.Course
import com.example.test_courses_app.domain.repository.impl.CoursesRepositoryImpl
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class MainViewModel(
    private val repository: CoursesRepositoryImpl
) : ViewModel() {
    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private var originalCourses: List<Course> = emptyList()

    init {
        loadCourses()
        observeCoursesWithFavorites()
    }

    private fun observeCoursesWithFavorites() {
        viewModelScope.launch {
            repository.getCoursesWithFavorites()
                .catch { e -> _errorMessage.value = e.message }
                .collect { courses ->
                    _courses.value = courses
                    _isLoading.value = false
                }
        }
    }

    fun loadCourses() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getCourses()
                .onSuccess { courses ->
                    originalCourses = courses
                    _errorMessage.value = null
                }
                .onFailure { error ->
                    _errorMessage.value = error.message ?: "Ошибка сети"
                    _isLoading.value = false
                }
        }
    }

    fun sortByDate(descending: Boolean) {
        _courses.value = if (descending) {
            originalCourses.sortedByDescending { it.publishDate.toMillis() }
        } else {
            originalCourses
        }
    }

    fun toggleFavorite(course: Course) {
        viewModelScope.launch {
            repository.toggleFavorite(course)
        }
    }

    fun retry() = loadCourses()

    private fun String.toMillis(): Long = try {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this)?.time ?: 0L
    } catch (e: Exception) {
        0L
    }
}

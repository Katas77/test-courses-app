package com.example.test_courses_app.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_courses_app.domain.Course
import com.example.test_courses_app.domain.repository.CoursesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: CoursesRepository
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
    }

    fun loadCourses() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getCourses()
                .onSuccess { courses ->
                    originalCourses = courses
                    _courses.value = courses
                    _errorMessage.value = null
                }
                .onFailure { error ->
                    _errorMessage.value = error.message ?: "Ошибка сети"
                }
            _isLoading.value = false
        }
    }

    fun sortByDate(descending: Boolean) {
        _courses.value = if (descending) {
            originalCourses.sortedByDescending { it.publishDateMillis() }
        } else {
            originalCourses
        }
    }

    fun toggleFavorite(course: Course) {
        viewModelScope.launch {
            repository.toggleFavorite(course)
            val current = _courses.value
            _courses.value = current.map {
                if (it.id == course.id) it.copy(hasLike = !it.hasLike) else it
            }
        }
    }

    fun retry() {
        loadCourses()
    }
}
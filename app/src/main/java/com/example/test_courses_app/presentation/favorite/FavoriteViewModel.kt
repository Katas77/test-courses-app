package com.example.test_courses_app.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_courses_app.domain.Course
import com.example.test_courses_app.domain.repository.CoursesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: CoursesRepository
) : ViewModel() {

    val favorites: StateFlow<List<Course>> = repository
        .getFavorites()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun removeFromFavorites(course: Course) {
        viewModelScope.launch {
            repository.toggleFavorite(course)
        }
    }
}
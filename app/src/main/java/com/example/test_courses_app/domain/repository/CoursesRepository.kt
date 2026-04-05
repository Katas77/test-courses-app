package com.example.test_courses_app.domain.repository

import com.example.test_courses_app.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {
    suspend fun getCourses(): Result<List<Course>>
    suspend fun toggleFavorite(course: Course)
    suspend fun isFavorite(courseId: Int): Boolean
    fun getFavorites(): Flow<List<Course>>
}
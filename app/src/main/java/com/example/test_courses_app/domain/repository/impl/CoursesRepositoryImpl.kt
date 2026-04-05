package com.example.test_courses_app.domain.repository.impl

import com.example.test_courses_app.core.database.dao.FavoriteDao
import com.example.test_courses_app.core.network.api.CoursesApi
import com.example.test_courses_app.domain.model.Course
import com.example.test_courses_app.domain.repository.CoursesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class CoursesRepositoryImpl(
    private val api: CoursesApi,
    private val favoriteDao: FavoriteDao
) : CoursesRepository {
    private val _apiCourses = MutableStateFlow<List<Course>>(emptyList())

    override suspend fun getCourses(): Result<List<Course>> = try {
        val response = api.getCourses()
        val courses = response.courses.map { it.toDomain() }
        _apiCourses.value = courses
        val favoriteIds = favoriteDao.getFavoriteIds()
        val updated = courses.map { it.copy(hasLike = it.id in favoriteIds) }
        Result.success(updated)
    } catch (e: Exception) {
        Result.failure(e)
    }

    fun getCoursesWithFavorites(): Flow<List<Course>> = combine(
        _apiCourses,
        favoriteDao.getFavorites()
    ) { courses, favorites ->
        val favoriteIds = favorites.map { it.courseId }.toSet()
        courses.map { it.copy(hasLike = it.id in favoriteIds) }
    }

    override suspend fun toggleFavorite(course: Course) {
        if (favoriteDao.isFavorite(course.id)) {
            favoriteDao.removeFromFavorites(course.toFavoriteEntity())
        } else {
            favoriteDao.addToFavorites(course.toFavoriteEntity())
        }
    }

    override suspend fun isFavorite(courseId: Int): Boolean = favoriteDao.isFavorite(courseId)

    override fun getFavorites(): Flow<List<Course>> = favoriteDao.getFavorites().map { entities ->
        entities.map { entity ->
            Course(
                id = entity.courseId,
                title = entity.title,
                text = entity.text,
                price = entity.price,
                rate = entity.rate,
                startDate = entity.startDate,
                hasLike = true,
                publishDate = entity.publishDate
            )
        }
    }
}

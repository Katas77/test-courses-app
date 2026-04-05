package com.example.test_courses_app.domain.model

import com.example.test_courses_app.core.database.entity.FavoriteCourseEntity
import java.text.SimpleDateFormat
import java.util.Locale

data class Course(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String
) {
    fun toFavoriteEntity(): FavoriteCourseEntity = FavoriteCourseEntity(
        courseId = id,
        title = title,
        text = text,
        price = price,
        rate = rate,
        startDate = startDate,
        publishDate = publishDate
    )

    fun publishDateMillis(): Long {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return try {
            format.parse(publishDate)?.time ?: 0L
        } catch (e: Exception) {
            0L
        }
    }
}
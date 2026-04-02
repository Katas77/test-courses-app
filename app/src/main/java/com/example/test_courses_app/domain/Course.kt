package com.example.test_courses_app.domain

import java.util.Calendar

data class Course(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String  // формат "YYYY-MM-DD"
) {

    fun publishDateMillis(): Long = runCatching {
        val parts = publishDate.split("-")
        if (parts.size == 3) {
            val year = parts[0].toIntOrNull() ?: return@runCatching 0L
            val month = (parts[1].toIntOrNull() ?: 1) - 1  // месяц от 0
            val day = parts[2].toIntOrNull() ?: 1

            val calendar = Calendar.getInstance()
            calendar.set(year, month, day, 0, 0, 0)
            calendar.timeInMillis
        } else {
            0L
        }
    }.getOrDefault(0L)
}
package com.example.test_courses_app.core.network.dto

import com.example.test_courses_app.domain.Course
import com.google.gson.annotations.SerializedName


data class CoursesResponse(
    @SerializedName("courses")
    val courses: List<CourseDto>
)


data class CourseDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("text") val text: String,
    @SerializedName("price") val price: String,
    @SerializedName("rate") val rate: String,
    @SerializedName("startDate") val startDate: String,
    @SerializedName("hasLike") val hasLike: Boolean,
    @SerializedName("publishDate") val publishDate: String
) {

    fun toDomain(): Course {
        return Course(
            id = id,
            title = title,
            text = text,
            price = price,
            rate = rate,
            startDate = startDate,
            hasLike = hasLike,
            publishDate = publishDate
        )
    }
}
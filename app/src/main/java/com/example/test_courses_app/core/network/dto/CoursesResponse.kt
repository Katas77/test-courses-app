package com.example.test_courses_app.core.network.dto

import com.google.gson.annotations.SerializedName

data class CoursesResponse(
    @SerializedName("courses") val courses: List<CourseDto>
)
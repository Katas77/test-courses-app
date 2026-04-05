package com.example.test_courses_app.core.network.api

import com.example.test_courses_app.core.network.dto.CoursesResponse
import retrofit2.http.GET

interface CoursesApi {
    @GET("uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download")
    suspend fun getCourses(): CoursesResponse
}
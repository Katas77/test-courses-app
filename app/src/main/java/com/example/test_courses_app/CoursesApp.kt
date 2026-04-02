package com.example.test_courses_app

import android.app.Application
import com.example.test_courses_app.core.database.AppDatabase
import com.example.test_courses_app.core.network.ApiClient

class CoursesApp : Application() {

    companion object {
        lateinit var instance: CoursesApp
            private set
    }


    val apiClient by lazy { ApiClient.create() }
    
    val database by lazy {
        AppDatabase.getInstance(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
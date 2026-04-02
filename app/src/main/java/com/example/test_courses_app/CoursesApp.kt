package com.example.test_courses_app

import android.app.Application
import com.example.test_courses_app.di.appModule
import com.example.test_courses_app.di.databaseModule
import com.example.test_courses_app.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CoursesApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CoursesApp)
            modules(networkModule, databaseModule, appModule)
        }
    }
}
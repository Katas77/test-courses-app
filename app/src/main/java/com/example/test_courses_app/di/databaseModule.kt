package com.example.test_courses_app.di

import android.content.Context
import androidx.room.Room
import com.example.test_courses_app.core.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "courses_db"
        ).build()
    }
    single { get<AppDatabase>().favoriteDao() }
}
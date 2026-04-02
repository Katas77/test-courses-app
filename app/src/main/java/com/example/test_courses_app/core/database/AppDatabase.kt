package com.example.test_courses_app.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.test_courses_app.core.database.entity.FavoriteCourseEntity

@Database(entities = [FavoriteCourseEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}
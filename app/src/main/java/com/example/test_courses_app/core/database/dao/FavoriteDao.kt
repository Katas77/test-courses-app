package com.example.test_courses_app.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.test_courses_app.core.database.entity.FavoriteCourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_courses ORDER BY addedAt DESC")
    fun getFavorites(): Flow<List<FavoriteCourseEntity>>

    @Query("SELECT courseId FROM favorite_courses")
    suspend fun getFavoriteIds(): List<Int>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addToFavorites(course: FavoriteCourseEntity)

    @Delete
    suspend fun removeFromFavorites(course: FavoriteCourseEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_courses WHERE courseId = :courseId)")
    suspend fun isFavorite(courseId: Int): Boolean
}
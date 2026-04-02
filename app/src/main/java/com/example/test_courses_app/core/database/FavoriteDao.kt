package com.example.test_courses_app.core.database

import androidx.room.*
import com.example.test_courses_app.core.database.entity.FavoriteCourse
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    
    @Query("SELECT * FROM favorite_courses ORDER BY addedAt DESC")
    fun getFavorites(): Flow<List<FavoriteCourse>>
    
    @Query("SELECT courseId FROM favorite_courses")
    suspend fun getFavoriteIds(): List<Int>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(course: FavoriteCourse)
    
    @Delete
    suspend fun removeFromFavorites(course: FavoriteCourse)
    
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_courses WHERE courseId = :courseId)")
    suspend fun isFavorite(courseId: Int): Boolean
}
package com.example.eduspark.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AttemptDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(attempt: QuizAttemptEntity)

    @Query("SELECT * FROM quiz_attempts ORDER BY completedAt DESC")
    fun observeAll(): Flow<List<QuizAttemptEntity>>

    @Query("DELETE FROM quiz_attempts")
    suspend fun clearAll()
}

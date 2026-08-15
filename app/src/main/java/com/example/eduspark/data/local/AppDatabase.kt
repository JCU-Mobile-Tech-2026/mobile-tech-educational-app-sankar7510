package com.example.eduspark.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [QuizAttemptEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun attemptDao(): AttemptDao
}

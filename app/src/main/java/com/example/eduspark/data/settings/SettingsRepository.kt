package com.example.eduspark.data.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.eduspark.model.AppSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "eduspark_settings")

@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object Keys {
        val difficulty = stringPreferencesKey("difficulty")
        val sound = booleanPreferencesKey("sound")
        val reminders = booleanPreferencesKey("reminders")
    }

    val settings: Flow<AppSettings> = context.dataStore.data.map { prefs ->
        AppSettings(
            difficulty = prefs[Keys.difficulty] ?: "easy",
            soundEnabled = prefs[Keys.sound] ?: true,
            remindersEnabled = prefs[Keys.reminders] ?: false
        )
    }

    suspend fun setDifficulty(value: String) {
        context.dataStore.edit { it[Keys.difficulty] = value }
    }

    suspend fun setSoundEnabled(value: Boolean) {
        context.dataStore.edit { it[Keys.sound] = value }
    }

    suspend fun setRemindersEnabled(value: Boolean) {
        context.dataStore.edit { it[Keys.reminders] = value }
    }
}

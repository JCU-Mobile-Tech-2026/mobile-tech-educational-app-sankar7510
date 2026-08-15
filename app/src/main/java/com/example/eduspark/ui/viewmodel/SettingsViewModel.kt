package com.example.eduspark.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eduspark.data.settings.SettingsRepository
import com.example.eduspark.model.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {
    val settings: StateFlow<AppSettings> = repository.settings.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        AppSettings()
    )

    fun setDifficulty(value: String) = viewModelScope.launch { repository.setDifficulty(value) }
    fun setSound(value: Boolean) = viewModelScope.launch { repository.setSoundEnabled(value) }
    fun setReminders(value: Boolean) = viewModelScope.launch { repository.setRemindersEnabled(value) }
}

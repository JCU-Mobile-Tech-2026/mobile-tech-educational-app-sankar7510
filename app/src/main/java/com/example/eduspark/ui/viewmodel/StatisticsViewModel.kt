package com.example.eduspark.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eduspark.data.local.QuizAttemptEntity
import com.example.eduspark.data.repository.EducationRepository
import com.example.eduspark.model.StatsSummary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val repository: EducationRepository
) : ViewModel() {
    val stats: StateFlow<StatsSummary> = repository.observeStats().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        StatsSummary()
    )

    val attempts: StateFlow<List<QuizAttemptEntity>> = repository.observeAttempts().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        emptyList()
    )

    fun clearStats() = viewModelScope.launch { repository.clearStats() }
}

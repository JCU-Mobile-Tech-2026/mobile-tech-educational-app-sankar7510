package com.example.eduspark.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eduspark.data.repository.EducationRepository
import com.example.eduspark.data.settings.SettingsRepository
import com.example.eduspark.model.QuizQuestion
import com.example.eduspark.util.QuizScorer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class QuizUiState(
    val isLoading: Boolean = false,
    val questions: List<QuizQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val selectedAnswer: String? = null,
    val score: Int = 0,
    val finished: Boolean = false,
    val saved: Boolean = false,
    val error: String? = null,
    val difficulty: String = "easy"
) {
    val currentQuestion: QuizQuestion? get() = questions.getOrNull(currentIndex)
}

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repository: EducationRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    init { loadQuiz() }

    fun loadQuiz() {
        viewModelScope.launch {
            val difficulty = settingsRepository.settings.first().difficulty
            _uiState.value = QuizUiState(isLoading = true, difficulty = difficulty)
            runCatching { repository.fetchQuestions(difficulty) }
                .onSuccess { questions ->
                    _uiState.value = QuizUiState(
                        questions = questions,
                        difficulty = difficulty
                    )
                }
                .onFailure { error ->
                    _uiState.value = QuizUiState(
                        error = error.message ?: "Could not load questions",
                        difficulty = difficulty
                    )
                }
        }
    }

    fun selectAnswer(answer: String) {
        if (_uiState.value.selectedAnswer == null && !_uiState.value.finished) {
            _uiState.value = _uiState.value.copy(selectedAnswer = answer)
        }
    }

    fun next() {
        val state = _uiState.value
        val question = state.currentQuestion ?: return
        val selected = state.selectedAnswer ?: return
        val newScore = state.score + if (QuizScorer.isCorrect(selected, question.correctAnswer)) 1 else 0
        val isLast = state.currentIndex == state.questions.lastIndex

        if (isLast) {
            _uiState.value = state.copy(score = newScore, finished = true)
            saveResult(newScore, state.questions.size, state.difficulty)
        } else {
            _uiState.value = state.copy(
                currentIndex = state.currentIndex + 1,
                selectedAnswer = null,
                score = newScore
            )
        }
    }

    private fun saveResult(score: Int, total: Int, difficulty: String) {
        viewModelScope.launch {
            repository.saveAttempt(score, total, difficulty)
            _uiState.value = _uiState.value.copy(saved = true)
        }
    }
}

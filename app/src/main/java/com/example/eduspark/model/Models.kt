package com.example.eduspark.model

data class QuizQuestion(
    val question: String,
    val correctAnswer: String,
    val answers: List<String>,
    val category: String,
    val difficulty: String
)

data class AppSettings(
    val difficulty: String = "easy",
    val soundEnabled: Boolean = true,
    val remindersEnabled: Boolean = false
)

data class StatsSummary(
    val totalQuizzes: Int = 0,
    val totalQuestions: Int = 0,
    val totalCorrect: Int = 0,
    val bestScore: Int = 0
) {
    val accuracy: Int
        get() = if (totalQuestions == 0) 0 else (totalCorrect * 100 / totalQuestions)
}

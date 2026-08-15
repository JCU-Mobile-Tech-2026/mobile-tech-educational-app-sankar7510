package com.example.eduspark.util

object QuizScorer {
    fun isCorrect(selected: String, correct: String): Boolean = selected == correct

    fun percentage(score: Int, total: Int): Int =
        if (total <= 0) 0 else (score * 100 / total)
}

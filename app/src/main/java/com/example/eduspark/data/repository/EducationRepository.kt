package com.example.eduspark.data.repository

import com.example.eduspark.data.local.AttemptDao
import com.example.eduspark.data.local.QuizAttemptEntity
import com.example.eduspark.data.remote.TriviaApi
import com.example.eduspark.model.QuizQuestion
import com.example.eduspark.model.StatsSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EducationRepository @Inject constructor(
    private val api: TriviaApi,
    private val attemptDao: AttemptDao
) {
    suspend fun fetchQuestions(difficulty: String, amount: Int = 10): List<QuizQuestion> {
        val response = api.getQuestions(amount = amount, difficulty = difficulty)
        if (response.response_code != 0) error("Trivia service returned code ${response.response_code}")

        return response.results.map { dto ->
            val correct = decode(dto.correct_answer)
            val answers = (dto.incorrect_answers.map(::decode) + correct).shuffled()
            QuizQuestion(
                question = decode(dto.question),
                correctAnswer = correct,
                answers = answers,
                category = decode(dto.category),
                difficulty = dto.difficulty
            )
        }
    }

    suspend fun saveAttempt(score: Int, total: Int, difficulty: String) {
        attemptDao.insert(
            QuizAttemptEntity(
                score = score,
                totalQuestions = total,
                difficulty = difficulty
            )
        )
    }

    fun observeAttempts(): Flow<List<QuizAttemptEntity>> = attemptDao.observeAll()

    fun observeStats(): Flow<StatsSummary> = attemptDao.observeAll().map { attempts ->
        StatsSummary(
            totalQuizzes = attempts.size,
            totalQuestions = attempts.sumOf { it.totalQuestions },
            totalCorrect = attempts.sumOf { it.score },
            bestScore = attempts.maxOfOrNull { it.score } ?: 0
        )
    }

    suspend fun clearStats() = attemptDao.clearAll()

    private fun decode(value: String): String =
        URLDecoder.decode(value, StandardCharsets.UTF_8.toString())
}

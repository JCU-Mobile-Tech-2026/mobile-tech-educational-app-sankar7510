package com.example.eduspark.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

data class TriviaResponse(
    val response_code: Int,
    val results: List<TriviaQuestionDto>
)

data class TriviaQuestionDto(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
)

interface TriviaApi {
    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int = 10,
        @Query("type") type: String = "multiple",
        @Query("difficulty") difficulty: String,
        @Query("encode") encode: String = "url3986"
    ): TriviaResponse
}

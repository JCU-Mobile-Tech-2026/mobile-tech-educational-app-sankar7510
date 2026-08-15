package com.example.eduspark

import com.example.eduspark.util.QuizScorer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class QuizScorerTest {
    @Test
    fun correctAnswer_returnsTrue() {
        assertTrue(QuizScorer.isCorrect("Canberra", "Canberra"))
    }

    @Test
    fun incorrectAnswer_returnsFalse() {
        assertFalse(QuizScorer.isCorrect("Sydney", "Canberra"))
    }

    @Test
    fun percentage_calculatesCorrectly() {
        assertEquals(80, QuizScorer.percentage(8, 10))
        assertEquals(0, QuizScorer.percentage(0, 0))
    }
}

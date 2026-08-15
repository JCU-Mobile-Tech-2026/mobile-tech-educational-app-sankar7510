package com.example.eduspark

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.eduspark.ui.screens.HomeScreen
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun homeScreen_showsCoreActions() {
        composeRule.setContent {
            HomeScreen(
                padding = androidx.compose.foundation.layout.PaddingValues(),
                onStartQuiz = {},
                onViewStats = {}
            )
        }

        composeRule.onNodeWithText("EduSpark").assertIsDisplayed()
        composeRule.onNodeWithText("Start Quiz").assertIsDisplayed()
        composeRule.onNodeWithText("View Progress").assertIsDisplayed()
    }
}

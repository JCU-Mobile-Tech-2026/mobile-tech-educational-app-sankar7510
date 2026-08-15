package com.example.eduspark.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    padding: PaddingValues,
    onStartQuiz: () -> Unit,
    onViewStats: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("EduSpark", style = MaterialTheme.typography.headlineLarge)
        Text(
            "Short, focused general-knowledge quizzes for secondary-school learners.",
            style = MaterialTheme.typography.bodyLarge
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Today's learning goal", style = MaterialTheme.typography.titleMedium)
                Text("Complete 10 questions, review your score, and use the statistics screen to track progress over time.")
            }
        }

        Button(onClick = onStartQuiz, modifier = Modifier.fillMaxWidth()) {
            Text("Start Quiz")
        }
        OutlinedButton(onClick = onViewStats, modifier = Modifier.fillMaxWidth()) {
            Text("View Progress")
        }
    }
}

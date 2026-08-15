package com.example.eduspark.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eduspark.ui.viewmodel.QuizViewModel
import com.example.eduspark.util.QuizScorer

@Composable
fun QuizScreen(
    padding: PaddingValues,
    viewModel: QuizViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("Learning Activity", style = MaterialTheme.typography.headlineMedium)

        when {
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                Text("Fetching fresh questions from the internet…")
            }
            state.error != null -> {
                Text("Unable to load the quiz: ${state.error}")
                Button(onClick = viewModel::loadQuiz) { Text("Try Again") }
            }
            state.finished -> {
                val total = state.questions.size
                val percent = QuizScorer.percentage(state.score, total)
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Quiz complete!", style = MaterialTheme.typography.headlineSmall)
                        Text("Score: ${state.score} / $total")
                        Text("Accuracy: $percent%")
                        Text(if (state.saved) "Progress saved locally." else "Saving progress…")
                    }
                }
                Button(onClick = viewModel::loadQuiz, modifier = Modifier.fillMaxWidth()) {
                    Text("Start New Quiz")
                }
            }
            else -> {
                val question = state.currentQuestion ?: return@Column
                Text("Difficulty: ${state.difficulty.replaceFirstChar { it.uppercase() }}")
                LinearProgressIndicator(
                    progress = { (state.currentIndex + 1).toFloat() / state.questions.size.coerceAtLeast(1) },
                    modifier = Modifier.fillMaxWidth()
                )
                Text("Question ${state.currentIndex + 1} of ${state.questions.size}")
                Text(question.category, style = MaterialTheme.typography.labelLarge)

                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        question.question,
                        modifier = Modifier.padding(18.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                question.answers.forEach { answer ->
                    OutlinedButton(
                        onClick = { viewModel.selectAnswer(answer) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (state.selectedAnswer == answer) "✓ $answer" else answer)
                    }
                }

                Button(
                    onClick = viewModel::next,
                    enabled = state.selectedAnswer != null,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (state.currentIndex == state.questions.lastIndex) "Finish" else "Next")
                }
            }
        }
    }
}

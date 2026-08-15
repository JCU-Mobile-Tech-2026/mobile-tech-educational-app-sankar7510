package com.example.eduspark.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eduspark.ui.viewmodel.StatisticsViewModel
import java.text.DateFormat
import java.util.Date

@Composable
fun StatisticsScreen(
    padding: PaddingValues,
    viewModel: StatisticsViewModel = hiltViewModel()
) {
    val stats by viewModel.stats.collectAsState()
    val attempts by viewModel.attempts.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { Text("User Statistics", style = MaterialTheme.typography.headlineMedium) }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard("Quizzes", stats.totalQuizzes.toString(), Modifier.weight(1f))
                StatCard("Accuracy", "${stats.accuracy}%", Modifier.weight(1f))
                StatCard("Best", stats.bestScore.toString(), Modifier.weight(1f))
            }
        }
        item { Text("Recent attempts", style = MaterialTheme.typography.titleMedium) }
        if (attempts.isEmpty()) {
            item { Text("No attempts yet. Complete a quiz to see progress here.") }
        } else {
            items(attempts.take(10), key = { it.id }) { attempt ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("${attempt.score}/${attempt.totalQuestions} • ${attempt.difficulty}")
                        Text(
                            DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)
                                .format(Date(attempt.completedAt)),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
            item {
                Button(onClick = viewModel::clearStats, modifier = Modifier.fillMaxWidth()) {
                    Text("Clear Statistics")
                }
            }
        }
    }
}

@Composable
private fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(value, style = MaterialTheme.typography.headlineSmall)
            Text(label, style = MaterialTheme.typography.bodySmall)
        }
    }
}

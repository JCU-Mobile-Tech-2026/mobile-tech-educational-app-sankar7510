package com.example.eduspark.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.eduspark.ui.screens.HomeScreen
import com.example.eduspark.ui.screens.QuizScreen
import com.example.eduspark.ui.screens.SettingsScreen
import com.example.eduspark.ui.screens.StatisticsScreen

private data class Destination(val route: String, val label: String)

@Composable
fun EduSparkNavHost() {
    val navController = rememberNavController()
    val destinations = listOf(
        Destination("home", "Home"),
        Destination("quiz", "Activity"),
        Destination("stats", "Statistics"),
        Destination("settings", "Settings")
    )
    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                destinations.forEach { destination ->
                    val icon = when (destination.route) {
                        "home" -> Icons.Default.Home
                        "quiz" -> Icons.Default.School
                        "stats" -> Icons.Default.BarChart
                        else -> Icons.Default.Settings
                    }
                    NavigationBarItem(
                        selected = currentRoute == destination.route,
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(icon, contentDescription = destination.label) },
                        label = { Text(destination.label) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier
        ) {
            composable("home") {
                HomeScreen(
                    padding = padding,
                    onStartQuiz = { navController.navigate("quiz") },
                    onViewStats = { navController.navigate("stats") }
                )
            }
            composable("quiz") { QuizScreen(padding) }
            composable("stats") { StatisticsScreen(padding) }
            composable("settings") { SettingsScreen(padding) }
        }
    }
}

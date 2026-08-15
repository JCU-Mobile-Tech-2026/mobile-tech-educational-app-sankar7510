package com.example.eduspark.ui.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eduspark.ui.viewmodel.SettingsViewModel
import com.example.eduspark.work.ReminderScheduler

@Composable
fun SettingsScreen(
    padding: PaddingValues,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val settings by viewModel.settings.collectAsState()
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        viewModel.setReminders(granted)
        ReminderScheduler.setEnabled(context, granted)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)

        Text("Difficulty", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("easy", "medium", "hard").forEach { difficulty ->
                FilterChip(
                    selected = settings.difficulty == difficulty,
                    onClick = { viewModel.setDifficulty(difficulty) },
                    label = { Text(difficulty.replaceFirstChar { it.uppercase() }) }
                )
            }
        }

        SettingSwitch(
            title = "Sound feedback",
            checked = settings.soundEnabled,
            onCheckedChange = viewModel::setSound
        )

        SettingSwitch(
            title = "Daily learning reminder",
            checked = settings.remindersEnabled,
            onCheckedChange = { enabled ->
                if (enabled && Build.VERSION.SDK_INT >= 33) {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    viewModel.setReminders(enabled)
                    ReminderScheduler.setEnabled(context, enabled)
                }
            }
        )

        Text(
            "Privacy: quiz results stay in the app's local Room database. No name, email, location, or advertising identifier is collected by EduSpark.",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            "The external trivia service is used only to download question content.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun SettingSwitch(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

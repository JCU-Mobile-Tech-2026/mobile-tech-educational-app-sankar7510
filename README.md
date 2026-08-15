# EduSpark – CP3406 / CP5307 Assessment 3 Educational App

EduSpark is an educational Android app for secondary-school learners. It presents short general-knowledge quizzes, downloads fresh question content from the Open Trivia Database, stores quiz attempts with Room, and displays progress statistics.

## Core assignment screens
1. Landing Page – app purpose, learning goal, shortcuts.
2. Activity Screen – 10-question online quiz.
3. Settings Screen – difficulty, sound preference, reminders.
4. User Statistics Screen – attempts, accuracy, best score.

## Architecture
- Kotlin + Jetpack Compose + Material 3
- Navigation Compose
- ViewModels
- Repository pattern
- Hilt dependency injection
- Retrofit networking
- Room database
- DataStore preferences
- Unit tests + Compose UI test

## CP5307 extras
- WorkManager periodic learning reminder
- Runtime POST_NOTIFICATIONS permission on Android 13+

## API
Questions are downloaded from Open Trivia DB. No API key is required.

## Ethical / professional design choices
- No account required.
- No personal identifiers collected by this app.
- Progress is stored locally.
- Notification permission is requested only when the user enables reminders.
- Reminders can be disabled at any time.
- Clear, readable Material 3 UI.

## Running the project
Open the root folder in Android Studio, allow Gradle to sync, then run on an emulator/device with internet access.

If Android Studio suggests a compatible update for AGP/Kotlin/dependencies, use the IDE migration tools and re-sync.


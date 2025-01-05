package com.example.insomease.models

data class SleepNote(
    val diaryId: Int? = null, // Nullable untuk ID otomatis
    val bedTime: String,
    val wakeTime: String,
    val bedtimeMood: String,
    val activitiesDuring: List<String>,
    val entryDate: String, // Format: YYYY-MM-DD
    val userId: Int // Foreign key untuk user
)

// SleepNoteModel.kt
package com.example.insomease.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

data class SleepNoteModel(
    val id: Int = 0,
    val entry_date: String,  // ISO string format
    val bed_time: String,    // ISO string format
    val wake_time: String,   // ISO string format
    val sleep_hours: Float,
    val mood: String,
    val userId: Int
) {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun toLocalDateTime(isoString: String): LocalDateTime {
            return LocalDateTime.ofInstant(
                Instant.parse(isoString),
                ZoneId.systemDefault()
            )
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun fromLocalDateTime(dateTime: LocalDateTime): String {
            return dateTime.atZone(ZoneId.systemDefault())
                .toInstant()
                .toString()
        }
    }
}
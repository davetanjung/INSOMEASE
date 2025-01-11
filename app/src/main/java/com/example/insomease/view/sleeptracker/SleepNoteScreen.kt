package com.example.insomease.view.sleeptracker

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.insomease.R
import com.example.insomease.models.SleepNoteModel
import com.example.insomease.viewmodel.SleepNoteViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateTimePickers(
    entryDate: LocalDateTime,
    bedTime: LocalDateTime,
    wakeTime: LocalDateTime,
    onEntryDateChanged: (LocalDateTime) -> Unit,
    onBedTimeChanged: (LocalDateTime) -> Unit,
    onWakeTimeChanged: (LocalDateTime) -> Unit
) {
    val context = LocalContext.current
    val dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Entry Date
        Button(
            onClick = {
                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        onEntryDateChanged(
                            entryDate
                                .withYear(year)
                                .withMonth(month + 1)
                                .withDayOfMonth(dayOfMonth)
                        )
                    },
                    entryDate.year,
                    entryDate.monthValue - 1,
                    entryDate.dayOfMonth
                ).show()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Text("Date: ${entryDate.format(dateFormatter)}")
        }

        // Bed Time
        Button(
            onClick = {
                TimePickerDialog(
                    context,
                    { _, hourOfDay, minute ->
                        onBedTimeChanged(
                            bedTime
                                .withHour(hourOfDay)
                                .withMinute(minute)
                        )
                    },
                    bedTime.hour,
                    bedTime.minute,
                    true
                ).show()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Text("Bed Time: ${bedTime.format(timeFormatter)}")
        }

        // Wake Time
        Button(
            onClick = {
                TimePickerDialog(
                    context,
                    { _, hourOfDay, minute ->
                        onWakeTimeChanged(
                            wakeTime
                                .withHour(hourOfDay)
                                .withMinute(minute)
                        )
                    },
                    wakeTime.hour,
                    wakeTime.minute,
                    true
                ).show()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Text("Wake Time: ${wakeTime.format(timeFormatter)}")
        }
    }
}

// Update SleepNoteScreen to use the new DateTimePickers
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SleepNoteScreen(
    sleepNoteViewModel: SleepNoteViewModel,
    onSaveSuccess: () -> Unit
) {
    val context = LocalContext.current
    var entryDate by remember { mutableStateOf(LocalDateTime.now()) }
    var bedTime by remember { mutableStateOf(LocalDateTime.now()) }
    var wakeTime by remember { mutableStateOf(LocalDateTime.now().plusHours(8)) }
    var mood by remember { mutableStateOf("Neutral") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Date and Time Pickers
        DateTimePickers(
            entryDate = entryDate,
            bedTime = bedTime,
            wakeTime = wakeTime,
            onEntryDateChanged = { entryDate = it },
            onBedTimeChanged = { bedTime = it },
            onWakeTimeChanged = { wakeTime = it }
        )

        // Mood selector
        MoodSelector(
            moods = listOf("Happy", "Calm", "Tired", "Anxious", "Neutral"),
            selectedMood = mood,
            onMoodSelected = { mood = it }
        )

        // Save button
        Button(
            onClick = {
                sleepNoteViewModel.createSleepNote(
                    entryDate = entryDate,
                    bedTime = bedTime,
                    wakeTime = wakeTime,
                    mood = mood,
                    userId = 1,
                    context = context
                )
                onSaveSuccess()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C92F8))
        ) {
            Text("Save", color = Color.White)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryItem(sleepNoteViewModel: SleepNoteViewModel) {
    val notes by sleepNoteViewModel.sleepNotes.collectAsState()
    val dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    LaunchedEffect(Unit) {
        sleepNoteViewModel.loadSleepNotes(userId = 1)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color(0xFF9C92F8),
                shape = RoundedCornerShape(8.dp)
            )
            .background(Color(0xFF030F25), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Sleep Note History",
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {
            items(items = notes, key = { note -> note.id }) { note ->
                NoteItem(
                    note = note,
                    dateFormatter = dateFormatter,
                    timeFormatter = timeFormatter
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun NoteItem(
    note: SleepNoteModel,
    dateFormatter: DateTimeFormatter,
    timeFormatter: DateTimeFormatter
) {
    val entryDate = SleepNoteModel.toLocalDateTime(note.entry_date)
    val bedTime = SleepNoteModel.toLocalDateTime(note.bed_time)
    val wakeTime = SleepNoteModel.toLocalDateTime(note.wake_time)

    Column {
        Text(
            text = "Date: ${entryDate.format(dateFormatter)}",
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.White
        )
        Text(
            text = "Bed Time: ${bedTime.format(timeFormatter)} || Wake Time: ${
                wakeTime.format(
                    timeFormatter
                )
            }",
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontSize = 14.sp,
            color = Color.White
        )
        Text(
            text = "Sleep Hours: ${note.sleep_hours}",
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontSize = 14.sp,
            color = Color.White
        )
        Text(
            text = "Mood: ${note.mood}",
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontSize = 14.sp,
            color = Color.White
        )
    }
}

@Composable
fun MoodSelector(moods: List<String>, selectedMood: String, onMoodSelected: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        moods.forEach { mood ->
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        color = if (mood == selectedMood) Color(0xFF514388) else Color(
                            0xFF030F25
                        ), shape = CircleShape
                    )
                    .border(
                        width = 2.dp,
                        color = if (mood == selectedMood) Color(0xFF9C92F8) else Color(
                            0xFF9C92F8
                        ),
                        shape = CircleShape
                    )
                    .clickable { onMoodSelected(mood) }, contentAlignment = Alignment.Center
            ) {
                Text(
                    text = mood,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontSize = 14.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

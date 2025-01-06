package com.example.insomease.view.sleeptracker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.insomease.R
import androidx.compose.material3.OutlinedTextField
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.insomease.models.SleepNoteModel
import com.example.insomease.viewModels.SleepNoteViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import com.example.insomease.AppContainer
import com.example.insomease.LunaireApplication
import com.example.insomease.repositories.SleepNoteRepository
import com.example.insomease.service.SleepNoteService
import com.example.insomease.viewmodel.SleepNoteViewModelFactory


@Composable
fun SleepNoteScreen(
) {
    val appContainer = (LocalContext.current.applicationContext as LunaireApplication)
        val viewModel: SleepNoteViewModel = viewModel(
//            factory = SleepNoteViewModelFactory(appContainer.sleepNoteRepository)
        )


    // State for form input
    var bedTime by remember { mutableStateOf(TextFieldValue("")) }
    var wakeTime by remember { mutableStateOf(TextFieldValue("")) }
    var date by remember { mutableStateOf(TextFieldValue("")) }
    var mood by remember { mutableStateOf("Neutral") }

//    val sleepNotes by viewModel.sleepNotes.observeAsState(emptyList())

    // Load sleep notes when the screen is first displayed
    LaunchedEffect(Unit) {
        viewModel.getAllSleepNotes()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Form input untuk catatan tidur
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            placeholder = { Text("e.g., 06/01/2025") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = bedTime,
            onValueChange = { bedTime = it },
            placeholder = { Text("e.g., 22:00") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = wakeTime,
            onValueChange = { wakeTime = it },
            placeholder = { Text("e.g., 06:00") },
            modifier = Modifier.fillMaxWidth()
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
                val sleepNote = SleepNoteModel(
                    id = 0, // ID bisa dihasilkan otomatis atau dari backend
                    date = date.text,
                    bedTime = bedTime.text,
                    wakeTime = wakeTime.text,
                    mood = mood,
                    sleepHours = 8.0f // Misalnya, menggunakan nilai default
                )
                viewModel.saveSleepNote(sleepNote)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C92F8)) // Button color
        ) {
            Text("Save", color = Color.White)
        }

        // Menampilkan daftar sleep notes
        LazyColumn(modifier = Modifier.fillMaxSize()) {
//            items(sleepNotes) { note ->
//                HistoryItem(note = note)
//            }
        }

    }
}

@Composable
fun HistoryItem(note: SleepNoteModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color(0xFF9C92F8), // Warna border
                shape = RoundedCornerShape(8.dp)
            )
            .background(Color(0xFF030F25), RoundedCornerShape(8.dp)) // Warna latar belakang
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

        Text(
            text = "Date: ${note.date}",
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.White
        )
        Text(
            text = "Bed Time: ${note.bedTime} || Wake Time: ${note.wakeTime}",
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontSize = 14.sp,
            color = Color.White
        )
        Text(
            text = "Sleep Hours: ${note.sleepHours}",
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
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        moods.forEach { mood ->
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        color = if (mood == selectedMood) Color(0xFF514388) else Color(0xFF030F25),
                        shape = CircleShape
                    )
                    .border(
                        width = 2.dp,
                        color = if (mood == selectedMood) Color(0xFF9C92F8) else Color(0xFF9C92F8),
                        shape = CircleShape
                    )
                    .clickable { onMoodSelected(mood) },
                contentAlignment = Alignment.Center
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



@Preview(showBackground = true)
@Composable
fun SleepNotePreview() {
    SleepNoteScreen()
}

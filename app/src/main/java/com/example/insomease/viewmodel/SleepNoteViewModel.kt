package com.example.insomease.viewmodel

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.insomease.LunaireApplication
import com.example.insomease.data.UserPreferencesRepository
import com.example.insomease.models.SleepNoteModel
import com.example.insomease.repositories.SleepNoteRepository
import com.example.insomease.viewModels.AuthenticationViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class SleepNoteViewModel(preferencesRepository: UserPreferencesRepository) : ViewModel() {
    private val repository = SleepNoteRepository()

    private val _sleepNotes = MutableStateFlow<List<SleepNoteModel>>(emptyList())
    val sleepNotes = _sleepNotes

    fun loadSleepNotes(userId: Int) {
        viewModelScope.launch {
            try {
                val notes = repository.getAllSleepNoteById(userId)
                _sleepNotes.value = notes
            } catch (e: Exception) {
                Log.e("SleepNoteViewModel", "Error loading sleep notes", e)
                // You might want to expose error state to UI
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createSleepNote(
        entryDate: LocalDateTime,
        bedTime: LocalDateTime,
        wakeTime: LocalDateTime,
        mood: String,
        userId: Int,
        context: Context
    ) {
        val sleepNote = SleepNoteModel(
            entry_date = SleepNoteModel.fromLocalDateTime(entryDate),
            bed_time = SleepNoteModel.fromLocalDateTime(bedTime),
            wake_time = SleepNoteModel.fromLocalDateTime(wakeTime),
            sleep_hours = calculateSleepHours(bedTime, wakeTime),
            mood = mood,
            userId = userId
        )

        viewModelScope.launch {
            try {
                val message = repository.createSleepNote(sleepNote, context)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                loadSleepNotes(userId)
            } catch (e: Exception) {
                Log.e("SleepNoteViewModel", "Error creating sleep note", e)
                Toast.makeText(context, "Failed to save sleep note", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateSleepHours(bedTime: LocalDateTime, wakeTime: LocalDateTime): Float {
        val duration = java.time.Duration.between(bedTime, wakeTime)
        return duration.toMinutes() / 60.0f
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as LunaireApplication)
                val authenticationRepository = application.container.authenticationRepository
                val userRepository = application.container.userRepository
                AuthenticationViewModel(
                    authenticationRepository,
                    userRepository
                )
            }
        }
    }
}





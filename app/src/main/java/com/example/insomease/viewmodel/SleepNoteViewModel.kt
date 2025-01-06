package com.example.insomease.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.insomease.LunaireApplication
import com.example.insomease.models.SleepNoteModel
import com.example.insomease.models.SleepNoteResponse
import com.example.insomease.repositories.SleepNoteRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class SleepNoteViewModel(
    private val sleepNoteRepository: SleepNoteRepository
) : ViewModel() {

    var sleepNotes: List<SleepNoteModel> by mutableStateOf(emptyList())

    var errorMessage by mutableStateOf("")
        private set

    fun saveSleepNote(sleepNote: SleepNoteModel) {
        viewModelScope.launch {
            try {
                val response = sleepNoteRepository.saveSleepNote(sleepNote) // Fungsi suspend langsung dipanggil
                if (response != null) {
                    getAllSleepNotes() // Refresh notes setelah menyimpan
                } else {
                    errorMessage = "Failed to save sleep note"
                }
            } catch (e: Exception) {
                errorMessage = "Save failed: ${e.localizedMessage}"
            }
        }
    }

    fun getAllSleepNotes() {
        viewModelScope.launch {
            try {
                val notes = sleepNoteRepository.getAllSleepNotes() // Fungsi suspend langsung dipanggil
                sleepNotes = notes.map {
                    SleepNoteModel(
                        id = it.id,
                        date = it.date,
                        bedTime = it.bedTime,
                        wakeTime = it.wakeTime,
                        mood = it.mood,
                        sleepHours = it.sleepHours
                    )
                }
            } catch (e: Exception) {
                errorMessage = "Failed to load sleep notes: ${e.localizedMessage}"
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LunaireApplication)
                val sleepNoteRepository = application.container.sleepNoteRepository
                SleepNoteViewModel(sleepNoteRepository)
            }
        }
    }
}

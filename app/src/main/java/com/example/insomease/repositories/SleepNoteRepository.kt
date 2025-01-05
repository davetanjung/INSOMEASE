package com.example.insomease.repository

import com.example.insomease.models.SleepNote
import com.example.insomease.services.SleepNoteService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SleepNoteRepository(private val service: SleepNoteService) {

    private val _sleepNotes = MutableStateFlow<List<SleepNote>>(emptyList())
    val sleepNotes: StateFlow<List<SleepNote>> get() = _sleepNotes

    suspend fun saveSleepNote(
        token: String,
        sleepNote: SleepNote,
        onComplete: (Boolean) -> Unit
    ) {
        try {
            val response = service.saveSleepNote(token, sleepNote).execute()
            if (response.isSuccessful) {
                onComplete(true)
            } else {
                onComplete(false)
            }
        } catch (e: Exception) {
            onComplete(false)
        }
    }

    suspend fun fetchSleepNotes(token: String) {
        try {
            val response = service.getSleepNotes(token).execute()
            if (response.isSuccessful) {
                _sleepNotes.value = response.body() ?: emptyList()
            }
        } catch (e: Exception) {
            _sleepNotes.value = emptyList()
        }
    }

    companion object {
        fun create(): SleepNoteRepository {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://your-api-url.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(SleepNoteService::class.java)
            return SleepNoteRepository(service)
        }
    }


}

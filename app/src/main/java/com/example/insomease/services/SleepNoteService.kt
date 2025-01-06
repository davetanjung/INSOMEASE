package com.example.insomease.service

import com.example.insomease.models.SleepNoteModel
import com.example.insomease.repositories.SleepNoteRepository

class SleepNoteService(private val repository: SleepNoteRepository) {

    // Mengambil semua catatan tidur
    suspend fun getAllNotes(): List<SleepNoteModel> {
        val responses = repository.getAllSleepNotes() // Mengambil List<SleepNoteResponse>
        return responses.map { response ->
            // Konversi dari SleepNoteResponse ke SleepNoteModel
            SleepNoteModel(
                id = response.id,
                date = response.date,
                bedTime = response.bedTime,
                wakeTime = response.wakeTime,
                mood = response.mood,
                sleepHours = response.sleepHours
            )
        }
    }

    // Menambahkan catatan tidur baru
    suspend fun addNote(note: SleepNoteModel): Boolean {
        val response = repository.saveSleepNote(note) // Mengembalikan SleepNoteResponse?
        return response != null // Mengembalikan true jika berhasil menyimpan
    }
}

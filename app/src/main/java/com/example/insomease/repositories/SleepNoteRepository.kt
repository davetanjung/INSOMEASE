package com.example.insomease.repositories

import android.content.Context
import com.example.insomease.models.SleepNoteModel
import com.example.insomease.services.SleepNoteService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SleepNoteRepository {
    private val service = AppClient.retrofit.create(SleepNoteService::class.java)

    suspend fun getAllSleepNoteById(userId: Int): List<SleepNoteModel> =
        withContext(Dispatchers.IO) {
            val response = service.getAllSleepNoteById(userId)
            response.data
        }

    suspend fun createSleepNote(sleepNote: SleepNoteModel, context: Context): String =
        withContext(Dispatchers.IO) {
            val response = service.createSleepNote(sleepNote)
            response.data
        }
}
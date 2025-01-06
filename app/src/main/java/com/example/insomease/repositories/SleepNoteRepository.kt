package com.example.insomease.repositories

import com.example.insomease.models.SleepNoteApiModel
import com.example.insomease.models.SleepNoteModel
import com.example.insomease.models.SleepNoteResponse
import com.example.insomease.services.SleepNoteAPI
import retrofit2.Call
import retrofit2.Response

interface SleepNoteRepository {
    suspend fun saveSleepNote(sleepNote: SleepNoteModel): SleepNoteResponse?
    suspend fun getAllSleepNotes(): List<SleepNoteResponse>
}

class NetworkSleepNoteRepository(
    private val sleepNoteAPI: SleepNoteAPI
) : SleepNoteRepository {
    override suspend fun saveSleepNote(sleepNote: SleepNoteModel): SleepNoteResponse? {
        val apiModel = SleepNoteApiModel(
            id = sleepNote.id,
            date = sleepNote.date,
            bedTime = sleepNote.bedTime,
            wakeTime = sleepNote.wakeTime,
            mood = sleepNote.mood,
            sleepHours = sleepNote.sleepHours
        )
        val response = sleepNoteAPI.saveSleepNote(apiModel)
        return if (response.isSuccessful) response.body() else null
    }

    override suspend fun getAllSleepNotes(): List<SleepNoteResponse> {
        val response = sleepNoteAPI.getAllSleepNotes()
        return if (response.isSuccessful) response.body() ?: emptyList() else emptyList()
    }
}

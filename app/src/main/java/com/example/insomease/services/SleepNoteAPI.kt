package com.example.insomease.services

import com.example.insomease.models.SleepNoteApiModel
import com.example.insomease.models.SleepNoteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SleepNoteAPI {
    @POST("sleep-notes")
    suspend fun saveSleepNote(@Body sleepNote: SleepNoteApiModel): Response<SleepNoteResponse>

    @GET("sleep-notes")
    suspend fun getAllSleepNotes(): Response<List<SleepNoteResponse>>
}


package com.example.insomease.services

import com.example.insomease.models.SleepNote
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface SleepNoteService {
    @POST("api/sleep-notes")
    fun saveSleepNote(
        @Header("Authorization") token: String,
        @Body sleepNote: SleepNote
    ): Call<Unit>

    @GET("api/sleep-notes")
    fun getSleepNotes(
        @Header("Authorization") token: String
    ): Call<List<SleepNote>>
}

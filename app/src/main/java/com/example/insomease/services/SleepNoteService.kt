package com.example.insomease.services

import com.example.insomease.models.ResponseModel
import com.example.insomease.models.SleepNoteModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SleepNoteService {
    @GET("api/sleepNote/{userId}")
    suspend fun getAllSleepNoteById(@Path("userId") userId: Int): ResponseModel<List<SleepNoteModel>>

    @POST("api/sleepNote")
    suspend fun createSleepNote(@Body sleepNote: SleepNoteModel): ResponseModel<String>
}
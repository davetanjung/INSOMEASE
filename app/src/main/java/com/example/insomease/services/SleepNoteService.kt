package com.example.insomease.services

import com.example.insomease.models.GeneralResponseModel
import com.example.insomease.models.TimePickerModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SleepNoteService {
    @POST("api/sleep_note")
    fun saveSleepNote(
        @Header("Authorization") token: String,
        @Body sleepNote: SleepNoteRequest
    ): Call<GeneralResponseModel>
}

data class SleepNoteRequest(
    val feeling: String,
    val activities: List<String>,
    val time: TimePickerModel
)

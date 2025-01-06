package com.example.insomease.models


import com.google.gson.annotations.SerializedName

data class SleepNoteResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("date") val date: String,
    @SerializedName("bed_time") val bedTime: String,
    @SerializedName("wake_time") val wakeTime: String,
    @SerializedName("mood") val mood: String,
    @SerializedName("sleep_hours") val sleepHours: Float
)

package com.example.insomease.models


data class SleepNoteApiModel(
    val id: Int,
    val date: String,
    val bedTime: String,
    val wakeTime: String,
    val mood: String,
    val sleepHours: Float
) {

}


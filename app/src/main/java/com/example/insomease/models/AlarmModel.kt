package com.example.insomease.models

data class AlarmModel(
    val time: String = "",
    val isActive: Boolean = true,
    val snoozeDuration: Int = 5 // dalam menit
)





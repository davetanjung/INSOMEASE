package com.example.insomease.services


import com.example.insomease.repositories.AlarmRepository

class AlarmService(private val repository: AlarmRepository) {

    // Fungsi untuk mendapatkan waktu alarm
    fun getAlarmTime(): String {
        return repository.alarmData.value.alarmTime
    }


}


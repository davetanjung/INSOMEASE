package com.example.insomease.repositories

import com.example.insomease.models.AlarmModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AlarmRepository {

    // StateFlow untuk menyimpan data AlarmModel
    private val _alarmData = MutableStateFlow(AlarmModel())
    val alarmData: StateFlow<AlarmModel> get() = _alarmData

    // Mengupdate waktu alarm
    fun setAlarmTime(time: String) {
        val currentData = _alarmData.value
        _alarmData.value = currentData.copy(alarmTime = time)
    }

    // Mengupdate noise lingkungan
    fun updateAmbientNoise(noise: String) {
        val currentData = _alarmData.value
        _alarmData.value = currentData.copy(ambientNoise = noise)
    }
}

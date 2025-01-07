package com.example.insomease.repositories

import com.example.insomease.models.AlarmModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class AlarmRepository {

    // StateFlow untuk menyimpan data AlarmModel
    private val _alarmData = MutableStateFlow(AlarmModel())
    val alarmData: StateFlow<AlarmModel> get() = _alarmData

    fun setAlarmTime(time: String) {
        if (time.matches(Regex("\\d{2}:\\d{2}"))) { // Validasi format HH:mm
            val currentData = _alarmData.value
            _alarmData.value = currentData.copy(alarmTime = time)
        } else {
            throw IllegalArgumentException("Invalid time format. Expected HH:mm")
        }
    }





}
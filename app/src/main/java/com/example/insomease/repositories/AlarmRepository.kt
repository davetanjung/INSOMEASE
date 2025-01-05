package com.example.insomease.repositories

import com.example.insomease.models.AlarmModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AlarmRepository {
    private val alarmData = MutableStateFlow(AlarmModel())

    fun getAlarm(): StateFlow<AlarmModel> {
        return alarmData
    }

    fun setAlarm(time: String) {
        alarmData.value = alarmData.value.copy(time = time, isActive = true)
    }

    fun dismissAlarm() {
        alarmData.value = alarmData.value.copy(isActive = false)
    }

    fun snoozeAlarm(snoozeDuration: Int) {
        val currentAlarmTime = alarmData.value.time.split(":").map { it.toInt() }
        val hour = currentAlarmTime[0]
        val minute = currentAlarmTime[1] + snoozeDuration

        val newTime = if (minute >= 60) {
            String.format("%02d:%02d", (hour + 1) % 24, minute % 60)
        } else {
            String.format("%02d:%02d", hour, minute)
        }

        alarmData.value = alarmData.value.copy(time = newTime, isActive = true)
    }
}


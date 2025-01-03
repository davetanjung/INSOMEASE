package com.example.insomease.repositories

import com.example.insomease.models.WakeUpTimeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WakeUpTimeRepository {
    private val wakeUpTimeData = MutableStateFlow(WakeUpTimeModel())

    fun getWakeUpTime(): StateFlow<WakeUpTimeModel> {
        return wakeUpTimeData
    }

    fun setWakeUpTime(newTime: String) {
        wakeUpTimeData.value = wakeUpTimeData.value.copy(
            selectedTime = newTime,
            isWakeUpSet = true
        )
    }
}

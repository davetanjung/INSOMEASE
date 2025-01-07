package com.example.insomease.repositories

import com.example.insomease.models.WakeUpTimeModel
import com.example.insomease.services.WakeUpTimeService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WakeUpTimeRepository(private val service: WakeUpTimeService = WakeUpTimeService()) {

    private var alarmTime: String? = null

    private val wakeUpTimeData = MutableStateFlow(
        WakeUpTimeModel(selectedTime = service.fetchDefaultWakeUpTime())
    )

    fun getWakeUpTime(): StateFlow<WakeUpTimeModel> {
        return wakeUpTimeData
    }

    fun setWakeUpTime(newTime: String) {
        if (service.validateWakeUpTime(newTime)) {
            if (wakeUpTimeData.value.selectedTime != newTime) {
                wakeUpTimeData.value = wakeUpTimeData.value.copy(
                    selectedTime = newTime,
                    isWakeUpSet = true
                )
                alarmTime = newTime // Perbarui alarmTime
            }
        } else {
            throw IllegalArgumentException("Invalid time format: $newTime")
        }
    }


    fun isAlarmSaved(): Boolean {
        return alarmTime != null
    }
    fun getSavedAlarmTime(): String? {
        return alarmTime
    }


}

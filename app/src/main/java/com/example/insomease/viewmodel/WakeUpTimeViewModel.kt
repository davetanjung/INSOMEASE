package com.example.insomease.viewmodel

import androidx.lifecycle.ViewModel
import com.example.insomease.models.WakeUpTimeModel
import com.example.insomease.repositories.AlarmRepository
import com.example.insomease.repositories.WakeUpTimeRepository
import kotlinx.coroutines.flow.StateFlow

class WakeUpTimeViewModel(
    private val wakeUpTimeRepository: WakeUpTimeRepository = WakeUpTimeRepository(),
    private val alarmRepository: AlarmRepository = AlarmRepository() // Tambahkan dependency
) : ViewModel() {

    val wakeUpTime: StateFlow<WakeUpTimeModel> = wakeUpTimeRepository.getWakeUpTime()

    fun setWakeUpTime(newTime: String) {
        wakeUpTimeRepository.setWakeUpTime(newTime)
        alarmRepository.setAlarmTime(newTime) // Sinkronkan ke AlarmRepository
    }
}

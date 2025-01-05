package com.example.insomease.viewmodel

import androidx.lifecycle.ViewModel
import com.example.insomease.models.WakeUpTimeModel
import com.example.insomease.repositories.WakeUpTimeRepository
import kotlinx.coroutines.flow.StateFlow

class WakeUpTimeViewModel(
    private val repository: WakeUpTimeRepository = WakeUpTimeRepository()
) : ViewModel() {

    val wakeUpTime: StateFlow<WakeUpTimeModel> = repository.getWakeUpTime()

    fun setWakeUpTime(newTime: String) {
        repository.setWakeUpTime(newTime)
    }
}

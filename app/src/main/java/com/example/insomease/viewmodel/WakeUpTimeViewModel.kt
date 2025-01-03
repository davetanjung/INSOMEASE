package com.example.insomease.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.insomease.models.WakeUpTimeModel
import com.example.insomease.repositories.WakeUpTimeRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WakeUpTimeViewModel(
    private val repository: WakeUpTimeRepository = WakeUpTimeRepository()
) : ViewModel() {

    val wakeUpTime: StateFlow<WakeUpTimeModel> = repository.getWakeUpTime()

    fun setWakeUpTime(newTime: String) {
        viewModelScope.launch {
            repository.setWakeUpTime(newTime)
        }
    }
}

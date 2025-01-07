package com.example.insomease.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.insomease.repositories.AlarmRepository
import com.example.insomease.repositories.WakeUpTimeRepository

class WakeUpTimeViewModelFactory(
    private val wakeUpTimeRepository: WakeUpTimeRepository,
    private val alarmRepository: AlarmRepository // Parameter tambahan
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WakeUpTimeViewModel::class.java)) {
            return WakeUpTimeViewModel(wakeUpTimeRepository, alarmRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


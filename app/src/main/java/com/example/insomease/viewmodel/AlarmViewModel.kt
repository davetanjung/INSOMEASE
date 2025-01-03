package com.example.insomease.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.insomease.repositories.AlarmRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class AlarmViewModel(private val repository: AlarmRepository = AlarmRepository()) : ViewModel() {

    val currentTime: StateFlow<String> = repository.alarmData.map { getCurrentTime() }.stateIn(
        viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = getCurrentTime()
    )

    val alarmTime: StateFlow<String> = repository.alarmData.map { it.alarmTime }.stateIn(
        viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = repository.alarmData.value.alarmTime
    )

    val ambientNoise: StateFlow<String> = repository.alarmData.map { it.ambientNoise }.stateIn(
        viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = repository.alarmData.value.ambientNoise
    )

    init {
        updateTimeEverySecond()
    }

    @SuppressLint("NewApi")
    private fun getCurrentTime(): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return LocalTime.now().format(formatter)
    }

    private fun updateTimeEverySecond() {
        viewModelScope.launch {
            while (true) {
                val currentData = repository.alarmData.value
                repository.setAlarmTime(currentData.alarmTime) // Menyimpan waktu terkini
                delay(1000)
            }
        }
    }

    fun setAlarmTime(time: String) {
        repository.setAlarmTime(time)
    }

    fun updateAmbientNoise(noise: String) {
        repository.updateAmbientNoise(noise)
    }
}

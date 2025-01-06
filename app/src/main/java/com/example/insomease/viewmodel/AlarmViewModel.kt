package com.example.insomease.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.insomease.repositories.AlarmRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class AlarmViewModel(private val repository: AlarmRepository = AlarmRepository()) : ViewModel() {

    // Untuk memantau apakah alarm sudah aktif
    private val _isAlarmTriggered = MutableStateFlow(false)
    val isAlarmTriggered: StateFlow<Boolean> = _isAlarmTriggered

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

    init {
        monitorTime()
    }

    @SuppressLint("NewApi")
    private fun getCurrentTime(): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return LocalTime.now().format(formatter)
    }

    private fun monitorTime() {
        viewModelScope.launch {
            while (true) {
                val now = getCurrentTime()
                if (now == repository.alarmData.value.alarmTime) {
                    _isAlarmTriggered.value = true
                }
                delay(1000)
            }
        }
    }

    fun dismissAlarm() {
        _isAlarmTriggered.value = false
    }

    @SuppressLint("NewApi")
    fun snoozeAlarm(minutes: Int = 5) {
        _isAlarmTriggered.value = false
        val currentAlarmTime = LocalTime.parse(repository.alarmData.value.alarmTime)
        val newTime = currentAlarmTime.plusMinutes(minutes.toLong())
        repository.setAlarmTime(newTime.format(DateTimeFormatter.ofPattern("HH:mm")))
    }


    fun setAlarmTime(time: String) {
        repository.setAlarmTime(time)
    }
}

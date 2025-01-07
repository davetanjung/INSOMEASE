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

    private val _isAlarmTriggered = MutableStateFlow(false)
    val isAlarmTriggered: StateFlow<Boolean> get() = _isAlarmTriggered


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
        updateTimeEverySecond()
        monitorAlarmTrigger()
    }

    private fun monitorAlarmTrigger() {
        viewModelScope.launch {
            while (true) {
                val currentTime = getCurrentTime()
                val alarmTime = repository.alarmData.value.alarmTime
                if (currentTime == alarmTime) {
                    _isAlarmTriggered.value = true
                }
                delay(1000)
            }
        }
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

    fun dismissAlarm() {
        _isAlarmTriggered.value = false
    }

    @SuppressLint("NewApi")
    fun snoozeAlarm(minutes: Int = 5) {
        dismissAlarm()
        val currentAlarmTime = LocalTime.parse(repository.alarmData.value.alarmTime)
        val newTime = currentAlarmTime.plusMinutes(minutes.toLong())
        repository.setAlarmTime(newTime.format(DateTimeFormatter.ofPattern("HH:mm")))
    }


}
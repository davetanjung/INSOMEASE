package com.example.insomease.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.insomease.data.UserPreferencesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class AlarmViewModel(
    private val preferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _isAlarmTriggered = MutableStateFlow(false)
    val isAlarmTriggered: StateFlow<Boolean> = _isAlarmTriggered

    @RequiresApi(Build.VERSION_CODES.O)
    val currentTime: StateFlow<String> = MutableStateFlow(getCurrentTime())

    val alarmTime: StateFlow<String> = preferencesRepository.wakeUpTime
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "00:00"
        )

    init {
        updateTimeEverySecond()
        monitorAlarmTrigger()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentTime(): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return LocalTime.now().format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateTimeEverySecond() {
        viewModelScope.launch {
            while (true) {
                (currentTime as MutableStateFlow).value = getCurrentTime()
                delay(1000)
            }
        }
    }

    private fun monitorAlarmTrigger() {
        viewModelScope.launch {
            while (true) {
                if (currentTime.value == alarmTime.value) {
                    _isAlarmTriggered.value = true
                }
                delay(1000)
            }
        }
    }

    fun dismissAlarm() {
        _isAlarmTriggered.value = false
    }

    fun snoozeAlarm(minutes: Int = 5) {
        viewModelScope.launch {
            dismissAlarm()
            val currentAlarmTime = LocalTime.parse(alarmTime.value)
            val newTime = currentAlarmTime.plusMinutes(minutes.toLong())
            preferencesRepository.saveWakeUpTime(
                newTime.format(DateTimeFormatter.ofPattern("HH:mm"))
            )
        }
    }
}

class AlarmViewModelFactory(
    private val preferencesRepository: UserPreferencesRepository
) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlarmViewModel::class.java)) {
            return AlarmViewModel(preferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
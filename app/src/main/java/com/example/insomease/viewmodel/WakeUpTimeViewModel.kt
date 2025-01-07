package com.example.insomease.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.insomease.models.WakeUpTimeModel
import com.example.insomease.repositories.AlarmRepository
import com.example.insomease.repositories.WakeUpTimeRepository
import com.example.insomease.route.listScreen
import com.example.insomease.view.sleeptracker.WakeUpTimeScreen
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WakeUpTimeViewModel(
    private val wakeUpTimeRepository: WakeUpTimeRepository,
    private val alarmRepository: AlarmRepository // Parameter tambahan
) : ViewModel() {

    val wakeUpTime: StateFlow<WakeUpTimeModel> = wakeUpTimeRepository.getWakeUpTime()

    fun setWakeUpTime(newTime: String) {
        wakeUpTimeRepository.setWakeUpTime(newTime)
        alarmRepository.setAlarmTime(newTime) // Sinkronkan ke AlarmRepository
    }

    fun wakeUpScreen(navController: NavController){
        viewModelScope.launch{
            navController.navigate(listScreen.HomeScreen.name)
        }

    }

    fun isAlarmSaved(): Boolean {
        return wakeUpTimeRepository.isAlarmSaved()
    }



}

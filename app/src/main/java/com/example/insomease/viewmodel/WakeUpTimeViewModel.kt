package com.example.insomease.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.insomease.data.UserPreferencesRepository
import com.example.insomease.route.listScreen
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WakeUpTimeViewModel(
    private val preferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val wakeUpTime: StateFlow<String> = preferencesRepository.wakeUpTime
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "00:00"
        )

    fun setWakeUpTime(newTime: String) {
        viewModelScope.launch {
            preferencesRepository.saveWakeUpTime(newTime)
        }
    }

    fun navigateToHome(navController: NavController) {
        navController.navigate(listScreen.HomeScreen.name)
    }
}
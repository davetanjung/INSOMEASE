package com.example.insomease.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.insomease.repositories.GoodNightRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class GoodNightViewModel(private val repository: GoodNightRepository) : ViewModel() {

    val message: StateFlow<String> = repository.goodNightModel
        .map { it.message }
        .stateIn(
            scope = viewModelScope, // Gunakan scope dari ViewModel
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = repository.goodNightModel.value.message // Nilai awal dari model
        )

    val isTracking: StateFlow<Boolean> = repository.goodNightModel
        .map { it.isTracking }
        .stateIn(
            scope = viewModelScope, // Gunakan scope dari ViewModel
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = repository.goodNightModel.value.isTracking // Nilai awal dari model
        )



    fun startTracking() {
        viewModelScope.launch {
            repository.updateTrackingStatus(true)
        }
    }

    fun stopTracking() {
        viewModelScope.launch {
            repository.updateTrackingStatus(false)
        }
    }
}

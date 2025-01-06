package com.example.insomease.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.insomease.repositories.WakeUpTimeRepository

class WakeUpTimeViewModelFactory(
    private val repository: WakeUpTimeRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WakeUpTimeViewModel::class.java)) {
            return WakeUpTimeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
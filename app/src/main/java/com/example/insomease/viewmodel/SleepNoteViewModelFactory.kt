package com.example.insomease.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.insomease.service.SleepNoteService
import com.example.insomease.viewModels.SleepNoteViewModel

class SleepNoteViewModelFactory(private val service: SleepNoteService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepNoteViewModel::class.java)) {
//            return SleepNoteViewModel(service) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
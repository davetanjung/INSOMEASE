package com.example.insomease.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.insomease.data.UserPreferencesRepository
import retrofit2.Retrofit

class SleepNoteViewModelFactory(
    private val preferencesRepository: UserPreferencesRepository,
    private val retrofit: Retrofit
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepNoteViewModel::class.java)) {
            return SleepNoteViewModel(retrofit) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
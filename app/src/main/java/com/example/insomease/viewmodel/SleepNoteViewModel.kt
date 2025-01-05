package com.example.insomease.viewmodel

import androidx.lifecycle.ViewModel
import com.example.insomease.models.TimePickerModel
import com.example.insomease.repositories.SleepNoteRepository
import kotlinx.coroutines.flow.StateFlow

class SleepNoteViewModel(private val repository: SleepNoteRepository = SleepNoteRepository()) : ViewModel() {
    val selectedFeeling: StateFlow<String> = repository.selectedFeeling
    val selectedActivities: StateFlow<List<String>> = repository.selectedActivities
    val sleepNoteTime: StateFlow<TimePickerModel> = repository.sleepNoteTime

    fun selectFeeling(feeling: String) {
        repository.selectFeeling(feeling)
    }

    fun toggleActivity(activity: String) {
        repository.toggleActivity(activity)
    }

    fun updateSleepNoteTime(hour: Int, minute: Int) {
        repository.updateSleepNoteTime(hour, minute)
    }

    fun saveSleepNote() {
        repository.saveSleepNote()
    }
}

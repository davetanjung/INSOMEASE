package com.example.insomease.repositories

import com.example.insomease.models.TimePickerModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SleepNoteRepository {
    private val _selectedFeeling = MutableStateFlow("")
    val selectedFeeling: StateFlow<String> get() = _selectedFeeling

    private val _selectedActivities = MutableStateFlow<List<String>>(emptyList())
    val selectedActivities: StateFlow<List<String>> get() = _selectedActivities

    private val _sleepNoteTime = MutableStateFlow(TimePickerModel())
    val sleepNoteTime: StateFlow<TimePickerModel> get() = _sleepNoteTime

    fun selectFeeling(feeling: String) {
        _selectedFeeling.value = feeling
    }

    fun toggleActivity(activity: String) {
        _selectedActivities.value = if (_selectedActivities.value.contains(activity)) {
            _selectedActivities.value - activity
        } else {
            _selectedActivities.value + activity
        }
    }

    fun updateSleepNoteTime(hour: Int, minute: Int) {
        _sleepNoteTime.value = TimePickerModel(hour, minute)
    }

    fun saveSleepNote() {
        // Simpan sleep note ke server atau database lokal di sini.
        // Contoh logika bisa ditambahkan jika ada API atau penyimpanan lokal.
    }
}

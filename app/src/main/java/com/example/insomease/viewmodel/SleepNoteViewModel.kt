package com.example.insomease.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.insomease.models.SleepNote
import com.example.insomease.repository.SleepNoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SleepNoteViewModel(private val repository: SleepNoteRepository) : ViewModel() {

    // State untuk bed time
    private val _bedTime = MutableStateFlow("")
    val bedTime: StateFlow<String> get() = _bedTime

    // State untuk wake time
    private val _wakeTime = MutableStateFlow("")
    val wakeTime: StateFlow<String> get() = _wakeTime

    // State untuk refreshment rating
    private val _refreshmentRating = MutableStateFlow(0)
    val refreshmentRating: StateFlow<Int> get() = _refreshmentRating

    // State untuk mood sebelum tidur
    private val _selectedFeeling = MutableStateFlow("")
    val selectedFeeling: StateFlow<String> get() = _selectedFeeling

    // State untuk aktivitas sebelum tidur
    private val _selectedActivities = MutableStateFlow<List<String>>(emptyList())
    val selectedActivities: StateFlow<List<String>> get() = _selectedActivities

    // State untuk status operasi
    private val _saveStatus = MutableStateFlow<Boolean?>(null)
    val saveStatus: StateFlow<Boolean?> get() = _saveStatus

    // Fungsi untuk mengatur bed time
    fun setBedTime(time: String) {
        _bedTime.value = time
    }

    // Fungsi untuk mengatur wake time
    fun setWakeTime(time: String) {
        _wakeTime.value = time
    }


    // Fungsi untuk memilih mood sebelum tidur
    fun selectFeeling(feeling: String) {
        _selectedFeeling.value = feeling
    }

    // Fungsi untuk toggle aktivitas sebelum tidur
    fun toggleActivity(activity: String) {
        val currentActivities = _selectedActivities.value.toMutableList()
        if (currentActivities.contains(activity)) {
            currentActivities.remove(activity)
        } else {
            currentActivities.add(activity)
        }
        _selectedActivities.value = currentActivities
    }

    // Fungsi untuk menyimpan Sleep Note
    fun saveSleepNote(token: String, userId: Int, onComplete: (Boolean) -> Unit) {
        // Buat SleepNote berdasarkan state saat ini
        val sleepNote = SleepNote(
            bedTime = bedTime.value,
            wakeTime = wakeTime.value,
            bedtimeMood = selectedFeeling.value,
            activitiesDuring = selectedActivities.value,
            entryDate = getCurrentDate(),
            userId = userId
        )

        // Lakukan operasi simpan dengan repository
        viewModelScope.launch {
            repository.saveSleepNote(token, sleepNote) { success ->
                _saveStatus.value = success
                onComplete(success)
            }
        }
    }

    // Fungsi untuk mendapatkan tanggal saat ini dalam format "YYYY-MM-DD"
    @SuppressLint("NewApi")
    private fun getCurrentDate(): String {
        val currentDate = java.time.LocalDate.now()
        return currentDate.toString()
    }
}

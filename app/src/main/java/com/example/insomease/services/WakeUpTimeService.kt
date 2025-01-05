package com.example.insomease.services

class WakeUpTimeService {

    // Fungsi untuk mendapatkan waktu bangun default dari sumber eksternal (simulasi)
    fun fetchDefaultWakeUpTime(): String {
        return ""
    }

    // Fungsi untuk memvalidasi waktu bangun yang dipilih
    fun validateWakeUpTime(selectedTime: String): Boolean {
        // Contoh validasi sederhana
        val timeParts = selectedTime.split(":")
        if (timeParts.size != 2) return false

        val hour = timeParts[0].toIntOrNull() ?: return false
        val minute = timeParts[1].toIntOrNull() ?: return false

        return hour in 0..23 && minute in 0..59
    }
}

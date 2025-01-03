package com.example.insomease.service

import com.example.insomease.repositories.GoodNightRepository

class GoodNightService(private val repository: GoodNightRepository) {

    fun setGoodNightMessage(message: String) {
        repository.updateMessage(message)
    }

    fun startSleepTracking() {
        repository.updateTrackingStatus(true)
    }

    fun stopSleepTracking() {
        repository.updateTrackingStatus(false)
    }
}

package com.example.insomease.repositories

import com.example.insomease.models.GoodNightModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GoodNightRepository {

    private val _goodNightModel = MutableStateFlow(GoodNightModel())
    val goodNightModel: StateFlow<GoodNightModel> get() = _goodNightModel

    fun updateMessage(message: String) {
        _goodNightModel.value = _goodNightModel.value.copy(message = message)
    }

    fun updateTrackingStatus(isTracking: Boolean) {
        _goodNightModel.value = _goodNightModel.value.copy(isTracking = isTracking)
    }
}

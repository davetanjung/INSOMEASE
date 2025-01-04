package com.example.insomease.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.insomease.LunaireApplication
import com.example.insomease.models.ActivityModel
import com.example.insomease.models.ActivityUserModel
import com.example.insomease.repositories.ActivityRepository
import com.example.insomease.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomePageViewModel(
    private val userRepository: UserRepository,
    private val activityRepository: ActivityRepository
) : ViewModel() {

    private val _activityModel = MutableStateFlow<MutableList<ActivityModel>>(mutableListOf())

    val activityModel: StateFlow<List<ActivityModel>>
        get() {
            return _activityModel.asStateFlow()
        }

    private val _activityUserModel = MutableStateFlow<MutableList<ActivityUserModel>>(mutableListOf())

    val activityUserModel: StateFlow<List<ActivityUserModel>>
        get() {
            return _activityUserModel.asStateFlow()
        }


    val token: StateFlow<String> = userRepository.currentUserToken
        .map { it.ifBlank { "Guest" } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = "Loading..."
        )

    val username: StateFlow<String> = userRepository.currentUsername
        .map { it.ifBlank { "Guest" } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = "Loading..."
        )

    fun fetchActivities(token: String, id: Int) {
        viewModelScope.launch {
            try {
                val response = activityRepository.getUserActivities(token, id)
                if (response.isSuccessful) {
                    val activities = response.body()?.data

                    // Convert List<ActivityUserModel> to MutableList<ActivityUserModel>
                    if (activities != null) {
                        _activityUserModel.value = activities.toMutableList()
                    } else {
                        _activityUserModel.value = mutableListOf() // Empty list if no activities
                    }

                } else {
                    Log.e("HomePageViewModel", "Error: ${response.code()}")
                    // Handle API error
                }
            } catch (e: Exception) {
                Log.e("HomePageViewModel", "Error fetching activities: ${e.message}")
                // Handle network or unexpected errors
            }
        }
    }



    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as LunaireApplication)
                val userRepository = application.container.userRepository
                val activityRepository = application.container.activityRepository
                HomePageViewModel(
                    userRepository,
                    activityRepository
                )
            }
        }

    }
}



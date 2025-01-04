package com.example.insomease.viewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.insomease.LunaireApplication
import com.example.insomease.models.ActivityModel
import com.example.insomease.models.ActivityRequest
import com.example.insomease.models.ActivityUserModel
import com.example.insomease.models.GeneralResponseModel
import com.example.insomease.repositories.ActivityRepository
import com.example.insomease.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    private val _createActivityResponse = mutableStateOf<GeneralResponseModel?>(null)
    val createActivityResponse = _createActivityResponse

    private val _errorState = mutableStateOf<String?>(null)
    val errorState = _errorState

    private val _showPopup = mutableStateOf(false)
    val showPopup: State<Boolean> = _showPopup


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

    fun togglePopup() {
        _showPopup.value = !_showPopup.value
    }

    // To close the popup directly (can be called when a cancel or close action occurs)
    fun closePopup() {
        _showPopup.value = false
    }

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

    fun createActivity(
        token: String,
        name: String,
        start_time: String,
        end_time: String,
        date: String,
        categoryId: Int,
        userId: Int
    ){
        val activityRequest = ActivityRequest(
            name = name,
            start_time = start_time,
            end_time = end_time,
            date = date,
            categoryId = categoryId,
            userId = userId
        )

        viewModelScope.launch {
            activityRepository.createActivity(
                token,
                activityRequest.name,
                activityRequest.start_time,
                activityRequest.end_time,
                activityRequest.date,
                activityRequest.categoryId,
                activityRequest.userId
            ).enqueue(object : Callback<GeneralResponseModel> {
                override fun onResponse(
                    call: Call<GeneralResponseModel>,
                    response: Response<GeneralResponseModel>
                ) {
                    if (response.isSuccessful) {
                        // Handle success
                        _createActivityResponse.value = response.body()
                    } else {

                        _errorState.value = "Error: ${response.code()}"
                        Log.e("CreateActivityViewModel", "Error: ${response.code()}")
                    }
                }

                override fun onFailure(p0: Call<GeneralResponseModel>, p1: Throwable) {
                    TODO("Not yet implemented")
                }

            })
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



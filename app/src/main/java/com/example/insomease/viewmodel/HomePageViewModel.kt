package com.example.insomease.viewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.example.insomease.LunaireApplication
import com.example.insomease.models.ActivityModel
import com.example.insomease.models.ActivityRequest
import com.example.insomease.models.ActivityUserModel
import com.example.insomease.models.GeneralResponseModel
import com.example.insomease.models.GetAllCategoryResponse
import com.example.insomease.models.UserResponse
import com.example.insomease.repositories.ActivityRepository
import com.example.insomease.repositories.CategoryRepository
import com.example.insomease.repositories.UserRepository
import com.example.insomease.route.listScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class HomePageViewModel(
    private val userRepository: UserRepository,
    private val activityRepository: ActivityRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    var nameInput by mutableStateOf("")
        private set
    var categoryId by mutableStateOf(0)
        private set
    var start_time by mutableStateOf("")
        private set
    var end_time by mutableStateOf("")
        private set
    var date by mutableStateOf("")
        private set

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

    private val _categories = mutableStateOf(GetAllCategoryResponse(emptyList()))
    val categories: State<GetAllCategoryResponse> = _categories

    private val _createActivityResponse = mutableStateOf<ActivityRequest?>(null)
    val createActivityResponse = _createActivityResponse

    private val _errorState = mutableStateOf<String?>(null)
    val errorState = _errorState

    var showPopUp by mutableStateOf(false)
        private set

    var showNextPopUp by mutableStateOf(false)
        private set

    fun updateName(name: String) {
        nameInput = name
    }

    fun updateCategoryId(categoryId: Int) {
        this.categoryId = categoryId
    }

    fun updateDate(newDate: String) {
        date = newDate
    }

    fun updateStartTime(newStartTime: String) {
        start_time = newStartTime
    }

    fun updateEndTime(newEndTime: String) {
        end_time = newEndTime
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

    fun togglePopup() {
        showPopUp = !showPopUp
    }

    fun toggleNextPopUp(){
        showNextPopUp = !showNextPopUp
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

    fun fetchCategories(token: String) {
        viewModelScope.launch {
            try {
                val response = categoryRepository.getAllCategories(token)

                if (response.isSuccessful) {
                    val categoriesData = response.body()?.data
                    if (categoriesData != null) {
                        _categories.value = GetAllCategoryResponse(categoriesData) // Update with fetched data
                    } else {
                        _categories.value = GetAllCategoryResponse(emptyList())
                    }
                } else {
                    Log.e("HomePageViewModel", "Error: ${response.code()}")
                    _errorState.value = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                Log.e("HomePageViewModel", "Error fetching categories: ${e.message}")
                _errorState.value = "Error: ${e.message}"
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
    ) {
        viewModelScope.launch {
            try {
                val call = activityRepository.createActivity(token, name, start_time, end_time, date, categoryId, userId)
                // Await result from Call
                val response = suspendCancellableCoroutine { continuation ->
                    call.enqueue(object : Callback<ActivityRequest> {
                        override fun onResponse(
                            call: Call<ActivityRequest>,
                            response: Response<ActivityRequest>
                        ) {
                            continuation.resume(response)
                        }

                        override fun onFailure(p0: Call<ActivityRequest>, t: Throwable) {
                            Log.e("CreateActivityViewModel", "API Failure: ${t.localizedMessage}")
                            _errorState.value = "Network Error: ${t.message}"
                            continuation.resumeWithException(t)
                        }
                    })

                    continuation.invokeOnCancellation {
                        call.cancel()
                    }
                }

                if (response.isSuccessful) {
                    _createActivityResponse.value = response.body()

                    // Navigate to the Home screen or Login page upon successful registration
                    togglePopup()
                } else {
                    _errorState.value = "Error: ${response.code()}"
                    Log.e("CreateActivityViewModel", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
//                errorMessage = "Registration failed: ${e.localizedMessage}"
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as LunaireApplication)
                val userRepository = application.container.userRepository
                val activityRepository = application.container.activityRepository
                val categoryRepository = application.container.categoryRepository
                HomePageViewModel(
                    userRepository,
                    activityRepository,
                    categoryRepository
                )
            }
        }

    }
}



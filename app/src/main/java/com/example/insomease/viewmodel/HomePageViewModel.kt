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
import com.example.insomease.models.GetAllCategoryResponse
import com.example.insomease.repositories.ActivityRepository
import com.example.insomease.repositories.CategoryRepository
import com.example.insomease.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar
import java.util.Date
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

    private val _currentUserId = MutableStateFlow(0)
    val currentUserId: StateFlow<Int> = _currentUserId

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

    private val _activityDetail = mutableStateOf<ActivityUserModel?>(null)
    val activityDetail: State<ActivityUserModel?> = _activityDetail

    private val _categories = mutableStateOf(GetAllCategoryResponse(emptyList()))
    val categories: State<GetAllCategoryResponse> = _categories

    private val _createActivityResponse = mutableStateOf<ActivityRequest?>(null)
    val createActivityResponse = _createActivityResponse

    private val _errorState = mutableStateOf<String?>(null)
    val errorState = _errorState

    private val _selectedDate = MutableStateFlow(Calendar.getInstance().time)
    val selectedDate: StateFlow<Date> = _selectedDate

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

    fun getUserId() {
        viewModelScope.launch {
            userRepository.currentUserId.collect { userId ->
                val userIdInt = userId.toIntOrNull()
                if (userIdInt != null) {
                    _currentUserId.value = userIdInt
                }
            }
        }
    }

    val token: StateFlow<String> = userRepository.currentUserToken
//        .map { it.ifBlank { "Guest" } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = "Loading..."
        )

    val username: StateFlow<String> = userRepository.currentUsername
//        .map { it.ifBlank { "Guest" } }
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

//    fun updateSelectedDate(offset: Int) {
//        val calendar = Calendar.getInstance()
//        calendar.time = _selectedDate.value
//        calendar.add(Calendar.DATE, offset)
//        _selectedDate.value = calendar.time
//
//
//        fetchActivitiesForDate(_selectedDate.value)
//    }
//
//    private fun fetchActivitiesForDate(date: Date) {
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//        val formattedDate = dateFormat.format(date)
//
//        viewModelScope.launch {
//            val userId = currentUserId.value
//            fetchActivities(token = token.value, id = userId, specificDate = formattedDate)
//        }
//    }



    fun fetchActivities(token: String, id: Int, specificDate: String? = null) {
        viewModelScope.launch {
            try {
                val response = activityRepository.getUserActivities(token, id, specificDate)
                if (response.isSuccessful) {
                    val activities = response.body()?.data
                    if (activities != null) {
                        _activityUserModel.value = activities.toMutableList()
                    } else {
                        _activityUserModel.value = mutableListOf()
                    }

                } else {
                    Log.e("HomePageViewModel", "Error: ${response.code()}")
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
                        _categories.value = GetAllCategoryResponse(categoriesData)
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

    fun getActivityById(token: String, activityId: Int) {
        viewModelScope.launch {
            try {
                val response = activityRepository.getActivityById(token, activityId)
                if (response.isSuccessful) {
                    val activityData = response.body()
                    _activityDetail.value = activityData
                } else {
                    Log.e("HomePageViewModel", "Error: ${response.code()}")
                    _errorState.value = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                Log.e("HomePageViewModel", "Error fetching activity: ${e.message}")
                _errorState.value = "Error: ${e.message}"
            }
        }
    }

    fun updateActivity(
        token: String,
        activityId: Int,
        name: String,
        start_time: String,
        end_time: String,
        date: String,
        categoryId: Int
    ) {
        viewModelScope.launch {
            try {
                val activityUserModel = ActivityUserModel(
                    activityId,
                    name,
                    start_time,
                    end_time,
                    date,
                    categoryId
                )

                val call = activityRepository.updateActivity(
                    token,
                    activityId,
                    activityUserModel.name,
                    activityUserModel.start_time,
                    activityUserModel.end_time,
                    activityUserModel.date,
                    activityUserModel.categoryId
                )
                // Await result from Call
                val response = suspendCancellableCoroutine { continuation ->
                    call.enqueue(object : Callback<ActivityUserModel> {
                        override fun onResponse(
                            call: Call<ActivityUserModel>,
                            response: Response<ActivityUserModel>
                        ) {
                            continuation.resume(response)
                        }

                        override fun onFailure(p0: Call<ActivityUserModel>, t: Throwable) {
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
                    // Update the current activity detail with the updated response body
                    _activityDetail.value = response.body()
                } else {
                    _errorState.value = "Error: ${response.code()}"
                    Log.e("HomePageViewModel", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _errorState.value = "Error: ${e.message}"
                Log.e("HomePageViewModel", "Error: ${e.message}")
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



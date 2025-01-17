package com.example.insomease.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.example.insomease.LunaireApplication
import com.example.insomease.models.UserResponse
import com.example.insomease.repositories.AuthenticationRepository
import com.example.insomease.repositories.UserRepository
import com.example.insomease.route.listScreen
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AuthenticationViewModel(
    private val authenticationRepository: AuthenticationRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    var emailInput by mutableStateOf("")
        private set
    var passwordInput by mutableStateOf("")
        private set
    var usernameInput by mutableStateOf("")
        private set
    var confirmPasswordInput by mutableStateOf("")
        private set
    var isPasswordVisible by mutableStateOf(false)
        private set
    var isConfirmPasswordVisible by mutableStateOf(false)
        private set
    var isButtonEnabled by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf("")
        private set

    // Input Handlers
    fun onEmailChange(newEmail: String) {
        emailInput = newEmail
        validateLoginForm()
    }

    fun onPasswordChange(newPassword: String) {
        passwordInput = newPassword
        validateLoginForm()
    }

    fun onUsernameChange(newUsername: String) {
        usernameInput = newUsername
        validateRegisterForm()
    }

    fun onConfirmPasswordChange(newPassword: String) {
        confirmPasswordInput = newPassword
        validateRegisterForm()
    }

    fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
    }

    fun toggleConfirmPasswordVisibility() {
        isConfirmPasswordVisible = !isConfirmPasswordVisible
    }

    private fun validateLoginForm() {
        isButtonEnabled = emailInput.isNotEmpty() && passwordInput.isNotEmpty()
    }

    private fun validateRegisterForm() {
        isButtonEnabled = emailInput.isNotEmpty() &&
                usernameInput.isNotEmpty() &&
                passwordInput.isNotEmpty() &&
                confirmPasswordInput == passwordInput
    }

    // Login Action
    fun loginUser(navController: NavController) {
        viewModelScope.launch {
            try {
                val call = authenticationRepository.login(emailInput, passwordInput)

                val response = suspendCancellableCoroutine<Response<UserResponse>> { continuation ->
                    call.enqueue(object : Callback<UserResponse> {
                        override fun onResponse(
                            call: Call<UserResponse>,
                            response: Response<UserResponse>
                        ) {
                            continuation.resume(response)
                        }

                        override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                            continuation.resumeWithException(t)
                        }
                    })

                    continuation.invokeOnCancellation {
                        call.cancel()
                    }
                }

                if (response.isSuccessful) {
                    val userData = response.body()
                    val userId = userData?.data?.id ?: 0
                    userRepository.saveUserToken(userData?.data?.token.orEmpty())
                    userRepository.saveUsername(userData?.data?.username.orEmpty())
                    userRepository.saveUserId(userId.toString())
                    // Navigate to the Home screen or Login page upon successful login
                    navController.navigate("${listScreen.HomeScreen.name}/$userId")
                } else {
                    errorMessage = response.message()
                }
            } catch (e: Exception) {
                errorMessage = "Login failed: ${e.localizedMessage}"
            }
        }
    }


    // Register Action
    fun registerUser(navController: NavController, name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val call = authenticationRepository.register(name, email, password)

                val response = suspendCancellableCoroutine { continuation ->
                    call.enqueue(object : Callback<UserResponse> {
                        override fun onResponse(
                            call: Call<UserResponse>,
                            response: Response<UserResponse>
                        ) {
                            continuation.resume(response)
                        }

                        override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                            continuation.resumeWithException(t)
                        }
                    })

                    continuation.invokeOnCancellation {
                        call.cancel()
                    }
                }

                if (response.isSuccessful) {
                    val userData = response.body()
                    userRepository.saveUserToken(userData?.data?.token.orEmpty())
                    userRepository.saveUsername(userData?.data?.username.orEmpty())

                    navController?.navigate(listScreen.LoginScreen.name)
                } else {
                    errorMessage = response.message()
                }
            } catch (e: Exception) {
                errorMessage = "Registration failed: ${e.localizedMessage}"
            }
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as LunaireApplication)
                val authenticationRepository = application.container.authenticationRepository
                val userRepository = application.container.userRepository
                AuthenticationViewModel(
                    authenticationRepository,
                    userRepository
                )
            }
        }
    }


}
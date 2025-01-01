package com.example.insomease.repositories

import com.example.insomease.models.UserResponse
import com.example.insomease.services.AuthenticationAPIService
import retrofit2.Call

interface AuthenticationRepository {
    fun register(username: String, email: String, password: String): Call<UserResponse>
    fun login(username: String, password: String): Call<UserResponse>
}

class NetworkAuthenticationRepository(
    private val authenticationAPIService: AuthenticationAPIService
): AuthenticationRepository{
    override fun register(username: String, email: String, password: String): Call<UserResponse> {
        var registerMap = HashMap<String, String>()

        registerMap["username"] = username
        registerMap["email"] = email
        registerMap["password"] = password

        return authenticationAPIService.register(registerMap)
    }

    override fun login(email: String, password: String): Call<UserResponse> {
        var loginMap = HashMap<String, String>()

        loginMap["email"] = email
        loginMap["password"] = password

        return authenticationAPIService.login(loginMap)
    }

}
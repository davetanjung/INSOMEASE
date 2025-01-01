package com.example.insomease.services

import retrofit2.Call
import com.example.insomease.models.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationAPIService {
    @POST("api/register")
    fun register(@Body registerMap: HashMap<String, String>): Call<UserResponse>

    @POST("login")
    fun login(@Body loginMap: HashMap<String, String>): Call<UserResponse>
}
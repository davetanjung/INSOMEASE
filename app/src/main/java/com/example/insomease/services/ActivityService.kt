package com.example.insomease.services

import com.example.insomease.models.ActivityModel
import com.example.insomease.models.ActivityRequest
import com.example.insomease.models.GeneralResponseModel
import com.example.insomease.models.GetActivityResponse
import com.example.insomease.models.GetAllActivityResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ActivityAPIService {
    @GET("api/activity")
    suspend fun getAllActivity(@Header("X-API-TOKEN") token: String): Response<GetAllActivityResponse>

    @GET("api/activity/{userId}")
    suspend fun getUserActivities(
        @Header("X-API-TOKEN") token: String,
        @Path("userId") id: Int
    ): Response<GetActivityResponse>

    @POST("api/activity")
    fun createActivity(
        @Header("X-API-TOKEN") token: String,
        @Body activityRequest: ActivityRequest
    ): Call<GeneralResponseModel>

    @PUT("api/activity/{id}")
    fun updateActivity(
        @Header("X-API-TOKEN") token: String,
        @Path("id") id: Int,
        @Body activityRequest: ActivityRequest
    ): Call<ActivityRequest>

    @DELETE("api/activity/{id}")
    fun deleteActivity(
        @Header("X-API-TOKEN") token: String,
        @Path("id") id: Int
    ): Call<GeneralResponseModel>
}

package com.example.insomease.repositories

import com.example.insomease.models.GeneralResponseModel
import com.example.insomease.models.GetAllActivityResponse
import com.example.insomease.models.ActivityRequest
import com.example.insomease.models.GetActivityResponse
import com.example.insomease.services.ActivityAPIService
import retrofit2.Call
import retrofit2.Response

interface ActivityRepository {
    suspend fun getAllActivities(token: String): Response<GetAllActivityResponse>
    suspend fun getUserActivities(token: String, id: Int): Response<GetActivityResponse>

    fun createActivity(
        token: String,
        title: String,
        start_time: String,
        end_time: String,
        date: String,
        userId: Int,
        categoryId: Int
    ): Call<ActivityRequest>

    fun updateActivity(
        token: String,
        activityId: Int,
        title: String,
        start_time: String,
        end_time: String,
        date: String
    ): Call<ActivityRequest>

    fun deleteActivity(token: String, activityId: Int): Call<GeneralResponseModel>
}

class NetworkActivityRepository(
    private val activityAPIService: ActivityAPIService
): ActivityRepository {

    // This method gets all activities
    override suspend fun getAllActivities(token: String): Response<GetAllActivityResponse> {
        return activityAPIService.getAllActivity(token)
    }

    override suspend fun getUserActivities(
        token: String,
        id: Int
    ): Response<GetActivityResponse> {
        return activityAPIService.getUserActivities(token, id)
    }

    // This method creates a new activity
    override fun createActivity(
        token: String,
        title: String,
        start_time: String,
        end_time: String,
        date: String,
        category_id: Int,
        user_id: Int
    ): Call<ActivityRequest> {
        val activityRequest = ActivityRequest(
            title,
            start_time,
            end_time,
            date,
            category_id,
            user_id
        )
        return activityAPIService.createActivity(token, activityRequest)
    }

    // This method updates an existing activity
    override fun updateActivity(
        token: String,
        activityId: Int,
        title: String,
        start_time: String,
        end_time: String,
        date: String
    ): Call<ActivityRequest> {
        val activityRequest = ActivityRequest(
            title,
            start_time,
            end_time,
            date,
        )
        return activityAPIService.updateActivity(token, activityId, activityRequest)
    }

    // This method deletes an existing activity
    override fun deleteActivity(token: String, activityId: Int): Call<GeneralResponseModel> {
        return activityAPIService.deleteActivity(token, activityId)
    }
}

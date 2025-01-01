package com.example.insomease.repositories

import com.example.insomease.models.GeneralResponseModel
import com.example.insomease.models.GetAllActivityResponse
import com.example.insomease.models.ActivityRequest
import com.example.insomease.services.ActivityAPIService
import retrofit2.Call

interface ActivityRepository {
    fun getAllActivities(token: String): Call<GetAllActivityResponse>

    fun createActivity(
        token: String,
        title: String,
        start_time: String,
        end_time: String,
        date: String,
        category_id: Int,
        user_id: Int
    ): Call<GeneralResponseModel>

    fun updateActivity(
        token: String,
        activityId: Int,
        title: String,
        start_time: String,
        end_time: String,
        date: String
    ): Call<GeneralResponseModel>

    fun deleteActivity(token: String, activityId: Int): Call<GeneralResponseModel>
}

class NetworkActivityRepository(
    private val activityAPIService: ActivityAPIService
): ActivityRepository {

    // This method gets all activities
    override fun getAllActivities(token: String): Call<GetAllActivityResponse> {
        return activityAPIService.getAllActivity(token)
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
    ): Call<GeneralResponseModel> {
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
    ): Call<GeneralResponseModel> {
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

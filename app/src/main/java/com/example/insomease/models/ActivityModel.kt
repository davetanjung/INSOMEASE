package com.example.insomease.models

import com.google.gson.annotations.SerializedName

// Response when retrieving multiple activities
data class GetAllActivityResponse(
    val data: List<ActivityModel>
)

data class GetActivityResponse(
    val data: ActivityModel
)


data class ActivityModel(
    val id: Int = 0,
    val name: String = "",
    val start_time: String = "",
    val end_time: String = "",
    val date: String = "",

    @SerializedName("user_id")
    val userId: Int = 0,

    @SerializedName("category_id")
    val categoryId: Int = 0
)

// Request structure for creating/updating an activity
data class ActivityRequest(
    val name: String = "",
    val start_time: String = "",
    val end_time: String = "",
    val date: String = "",

    @SerializedName("user_id")
    val userId: Int = 0,

    @SerializedName("category_id")
    val categoryId: Int = 0
)


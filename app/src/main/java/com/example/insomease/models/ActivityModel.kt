package com.example.insomease.models

import com.google.gson.annotations.SerializedName

// Response when retrieving multiple activities
data class GetAllActivityResponse(
    val data: List<ActivityModel>
)

data class GetActivityResponse(
    val data: List<ActivityUserModel>
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

data class ActivityUserModel (
    val id: Int = 0,
    val name: String = "",
    val start_time: String = "",
    val end_time: String = "",
    val date: String = "",

    val categoryId: Int = 0
)

// Request structure for creating/updating an activity
data class ActivityRequest(
    val name: String = "",
    val start_time: String = "",
    val end_time: String = "",
    val date: String = "",

    val categoryId: Int = 0,

val userId: Int = 0
)

fun convertTimeToMinutes(time: String): Int {
    val timeParts = time.split(":").map { it.toInt() }
    return timeParts[0] * 60 + timeParts[1] // returns time in minutes
}

fun calculateStartPercentage(startTime: String): Float {
    val totalMinutes = 1440 // Total minutes in a day
    val startMinutes = convertTimeToMinutes(startTime)
    return (startMinutes.toFloat() / totalMinutes) * 100 // Convert to percentage
}

fun calculateEndPercentage(endTime: String, startTime: String): Float {
    val totalMinutes = 1440
    val endMinutes = convertTimeToMinutes(endTime)
    val startMinutes = convertTimeToMinutes(startTime)
    return ((endMinutes - startMinutes).toFloat() / totalMinutes) * 100 // Duration percentage
}





package com.example.insomease.models

data class UserResponse (
    val data: UserModel
)

data class UserModel(
    val id: Number,
    val username: String,
    val token: String?
)
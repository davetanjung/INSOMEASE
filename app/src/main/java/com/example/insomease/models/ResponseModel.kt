package com.example.insomease.models

data class ResponseModel<T>(
    val data: T,
    val message: String? = null
)
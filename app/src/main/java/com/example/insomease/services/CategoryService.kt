package com.example.insomease.services

import com.example.insomease.models.GetAllCategoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface  CategoryService {
    @GET("api/category")
    suspend fun getAllCategories(@Header("X-API-TOKEN") token: String): Response<GetAllCategoryResponse>

}
package com.example.insomease.repositories

import com.example.insomease.models.GetAllActivityResponse
import com.example.insomease.models.GetAllCategoryResponse
import com.example.insomease.services.ActivityAPIService
import com.example.insomease.services.CategoryService
import retrofit2.Response

interface CategoryRepository {
    suspend fun getAllCategories(token: String): Response<GetAllCategoryResponse>

}

class NetworkCategoryRepository(
    private val categoryService: CategoryService
):CategoryRepository {
    override suspend fun getAllCategories(token: String): Response<GetAllCategoryResponse> {
        return categoryService.getAllCategories(token)
    }
}
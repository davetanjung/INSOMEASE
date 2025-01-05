package com.example.insomease.models

data class GetAllCategoryResponse(
    val data: List<CategoryModel>
)

data class CategoryModel(
    val id: Int = 0,
    val name: String = ""
)
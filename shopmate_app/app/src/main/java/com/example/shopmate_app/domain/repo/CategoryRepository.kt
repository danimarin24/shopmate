package com.example.shopmate_app.domain.repo

import com.example.shopmate_app.data.services.networkServices.CategoryService
import com.example.shopmate_app.domain.entities.newtworkEntities.CategoryEntity
import com.example.shopmate_app.domain.entities.providers.CategoryProvider
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val api: CategoryService){
    suspend fun getAllCategories(): List<CategoryEntity> {
        val response = api.getCategories()
        CategoryProvider.categories = response
        return response
    }
}
package com.example.shopmate_app.data.services.networkServices

import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.domain.entities.newtworkEntities.CategoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryService @Inject constructor(private val api: CategoryApiClient) {
    suspend fun getCategories(): List<CategoryEntity> {
        return withContext(Dispatchers.IO) {
            val response = api.getCategories(AppConstants.API_KEY)
            response.body() ?: emptyList()
        }
    }
}
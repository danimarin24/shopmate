package com.example.shopmate_app.data.services.networkServices

import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.domain.entities.newtworkEntities.CategoryEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface CategoryApiClient {
    // GET
    @GET(AppConstants.CATEGORY_ENDPOINT)
    suspend fun getCategories(@Header("x-api-key") apiKey: String): Response<List<CategoryEntity>>
}
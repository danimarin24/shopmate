package com.example.shopmate_app.data.services.networkServices

import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.domain.entities.newtworkEntities.ColorEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ColorApiClient {
    // GET
    @GET(AppConstants.COLOR_ENDPOINT)
    suspend fun getColors(@Header("x-api-key") apiKey: String): Response<List<ColorEntity>?>
}
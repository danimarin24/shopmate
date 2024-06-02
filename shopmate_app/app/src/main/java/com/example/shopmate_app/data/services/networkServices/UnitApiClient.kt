package com.example.shopmate_app.data.services.networkServices

import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.domain.entities.newtworkEntities.UnitEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UnitApiClient {
    // GET
    @GET(AppConstants.UNIT_ENDPOINT)
    suspend fun getUnits(@Header("x-api-key") apiKey: String): Response<List<UnitEntity>?>
}
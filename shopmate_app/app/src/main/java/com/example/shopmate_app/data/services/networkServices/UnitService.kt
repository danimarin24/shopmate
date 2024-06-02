package com.example.shopmate_app.data.services.networkServices

import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.domain.entities.newtworkEntities.UnitEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UnitService @Inject constructor(private val api: UnitApiClient) {
    suspend fun getUnits(): List<UnitEntity> {
        return withContext(Dispatchers.IO) {
            val response = api.getUnits(AppConstants.API_KEY)
            response.body() ?: emptyList()
        }
    }
}
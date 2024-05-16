package com.example.shopmate_app.data.services.networkServices

import android.util.Log
import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.domain.entities.newtworkEntities.ColorEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ColorService @Inject constructor(private val api: ColorApiClient) {
    suspend fun getColors(): List<ColorEntity> {
        return withContext(Dispatchers.IO) {
            val response = api.getColors(AppConstants.API_KEY)
            response.body() ?: emptyList()
        }
    }
}
package com.example.shopmate_app.domain.repo

import android.util.Log
import com.example.shopmate_app.data.services.networkServices.ColorService
import com.example.shopmate_app.domain.entities.newtworkEntities.ColorEntity
import com.example.shopmate_app.domain.entities.providers.ColorProvider
import javax.inject.Inject

class ColorRepository @Inject constructor(private val api: ColorService){
    suspend fun getAllColors(): List<ColorEntity> {
        val response = api.getColors()
        ColorProvider.colors = response
        return response
    }
}
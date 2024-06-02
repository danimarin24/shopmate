package com.example.shopmate_app.domain.repo

import com.example.shopmate_app.data.services.networkServices.UnitService
import com.example.shopmate_app.domain.entities.newtworkEntities.UnitEntity
import com.example.shopmate_app.domain.entities.providers.UnitProvider
import javax.inject.Inject

class UnitRepository @Inject constructor(private val api: UnitService){
    suspend fun getAllUnits(): List<UnitEntity> {
        val response = api.getUnits()
        UnitProvider.units = response
        return response
    }
}
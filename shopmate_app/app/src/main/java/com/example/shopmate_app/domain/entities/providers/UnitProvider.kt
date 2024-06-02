package com.example.shopmate_app.domain.entities.providers

import com.example.shopmate_app.domain.entities.newtworkEntities.UnitEntity

class UnitProvider {
    companion object {
        var units: List<UnitEntity>? = emptyList()
    }
}
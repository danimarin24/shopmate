package com.example.shopmate_app.domain.entities.providers

import com.example.shopmate_app.domain.entities.newtworkEntities.ColorEntity

class ColorProvider {
    companion object {
        var colors: List<ColorEntity>? = emptyList()
    }
}
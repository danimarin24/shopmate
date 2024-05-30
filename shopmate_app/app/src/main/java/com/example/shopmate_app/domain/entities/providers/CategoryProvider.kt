package com.example.shopmate_app.domain.entities.providers

import com.example.shopmate_app.domain.entities.newtworkEntities.CategoryEntity

class CategoryProvider {
    companion object {
        var categories: List<CategoryEntity>? = emptyList()
    }
}
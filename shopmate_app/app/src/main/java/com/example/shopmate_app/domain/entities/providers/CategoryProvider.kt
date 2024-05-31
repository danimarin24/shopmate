package com.example.shopmate_app.domain.entities.providers

import com.example.shopmate_app.domain.entities.newtworkEntities.CategoryEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ItemEntity

class CategoryProvider {
    companion object {
        var categories: List<CategoryEntity>? = emptyList()

        var selectedCategory: CategoryEntity? = null
    }
}
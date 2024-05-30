package com.example.shopmate_app.domain.entities.newtworkEntities

data class CategoryEntity(
    val categoryId: Int,
    val name: String,
    val icon: String,
    val updatedAt: String,
    val createdAt: String,
    val items: List<ItemEntity?>
)
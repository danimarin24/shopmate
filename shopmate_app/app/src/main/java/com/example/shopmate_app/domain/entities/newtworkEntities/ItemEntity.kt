package com.example.shopmate_app.domain.entities.newtworkEntities

data class ItemEntity(
    val itemId: Int,
    val name: String,
    val categoryId: Int?,
    val category: CategoryEntity?,
    val updatedAt: String,
    val createdAt: String,
)
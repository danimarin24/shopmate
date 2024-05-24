package com.example.shopmate_app.domain.entities.newtworkEntities

data class CategoryEntity(
    val categoryId: Int,
    val name: String,
    val icon: String,
    val createdBy: String,
    val assignedTo: String,
)
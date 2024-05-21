package com.example.shopmate_app.domain.entities.newtworkEntities

data class CardEntity(
    val cardId: Int,
    val name: String,
    val ownerId: Int,
    val isPublic: Int,
    val isTemplate: Int,
    val isArchived: Int?,
    val estimatedPrice: Float?,
    val colorId: Int
)
package com.example.shopmate_app.domain.entities.newtworkEntities

data class CardEntity(
    val cardId: Int,
    val cardName: String,
    val ownerId: Int,
    val isPublic: Int,
    val isTemplate: Int,
    val isArchived: Int?,
    val estimatedPrice: Float?,
    val color: ColorEntity
)
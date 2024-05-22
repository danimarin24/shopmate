package com.example.shopmate_app.domain.entities.newtworkEntities

data class ValidateShareLinkResponeEntity(
    val isValid: Boolean,
    val message: String,
    val cardId: Int,
    val userId: Int,
)
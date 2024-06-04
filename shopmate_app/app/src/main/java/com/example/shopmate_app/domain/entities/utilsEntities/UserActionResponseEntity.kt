package com.example.shopmate_app.domain.entities.utilsEntities

data class UserActionResponseEntity (
    val isActionPerformed: Boolean,
    val message: String,
    val userId: Int,
    val userActionId: Int,
)
package com.example.shopmate_app.domain.entities.newtworkEntities

data class BoardEntity(
    val boardId: Int?,
    val title: String,
    val ownerId: Int,
    val cards: List<CardEntity>?
)
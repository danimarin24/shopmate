package com.example.shopmate_app.domain.entities.dbEntities

import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity

data class DbBoardEntity(
    val boardId: Int?,
    val title: String,
    val ownerId: Int,
    val cards: List<CardEntity>
)
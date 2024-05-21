package com.example.shopmate_app.domain.entities.providers

import com.example.shopmate_app.domain.entities.newtworkEntities.BoardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity

class BoardProvider {
    companion object {
        var boards: List<BoardEntity> = emptyList()
        var cards: List<CardEntity> = emptyList()

        var board: BoardEntity? = null
    }
}
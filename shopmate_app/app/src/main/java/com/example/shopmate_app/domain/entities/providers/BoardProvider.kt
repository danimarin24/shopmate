package com.example.shopmate_app.domain.entities.providers

import com.example.shopmate_app.domain.entities.newtworkEntities.BoardEntity

class BoardProvider {
    companion object {
        var boards: List<BoardEntity>? = emptyList()

        var board: BoardEntity? = null
    }
}
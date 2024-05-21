package com.example.shopmate_app.domain.repo

import com.example.shopmate_app.data.services.networkServices.BoardService
import com.example.shopmate_app.domain.entities.newtworkEntities.BoardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.example.shopmate_app.domain.entities.providers.BoardProvider
import javax.inject.Inject

class BoardRepository @Inject constructor(private val boardService: BoardService) {
    suspend fun getBoardsByUser(userId: Int): List<BoardEntity> {
        val response = boardService.getBoardsByUser(userId)
            BoardProvider.boards = response
        return response
    }

    suspend fun getCardsByBoard(boardId: Int): List<CardEntity> {
        val response = boardService.getCardsByBoard(boardId)
        BoardProvider.cards = response
        return response
    }

    suspend fun addBoard(board: BoardEntity): BoardEntity {
        val response = boardService.addBoard(board)
        BoardProvider.board = response
        return response
    }
}
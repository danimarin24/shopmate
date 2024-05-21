package com.example.shopmate_app.domain.repo

import com.example.shopmate_app.data.services.networkServices.BoardService
import com.example.shopmate_app.domain.entities.newtworkEntities.BoardEntity
import com.example.shopmate_app.domain.entities.providers.BoardProvider
import javax.inject.Inject

class BoardRepository @Inject constructor(private val api: BoardService){

    suspend fun getBoardsByOwnerId(id: Int): List<BoardEntity>? {
        val response = api.getBoardsByOwnerId(id)
        BoardProvider.boards = response
        return response!!
    }

    suspend fun addBoard(board: BoardEntity): BoardEntity? {
        val response = api.addBoard(board)
        BoardProvider.board = response
        return response!!
    }

}
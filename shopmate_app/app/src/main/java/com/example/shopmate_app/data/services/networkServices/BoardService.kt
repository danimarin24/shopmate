package com.example.shopmate_app.data.services.networkServices

import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.domain.entities.newtworkEntities.BoardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BoardService @Inject constructor(private val api: BoardApiClient) {
    suspend fun getBoardsByUser(userId: Int): List<BoardEntity>  {
        return withContext(Dispatchers.IO) {
            val response = api.getBoardsByUser(userId, AppConstants.API_KEY)
            response.body()!!
        }
    }

    suspend fun getCardsByBoard(boardId: Int): List<CardEntity>  {
        return withContext(Dispatchers.IO) {
            val response = api.getCardsByBoard(boardId, AppConstants.API_KEY)
            response.body()!!
        }
    }

    suspend fun addBoard(board: BoardEntity): BoardEntity {
        return withContext(Dispatchers.IO) {
            val response = api.addBoard(board, AppConstants.API_KEY)
            if (response.isSuccessful) {
                response.body()!!
            } else {
                throw Exception("Error adding board: ${response.message()}")
            }
        }
    }
}
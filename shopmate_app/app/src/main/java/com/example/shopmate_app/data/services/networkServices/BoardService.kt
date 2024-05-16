package com.example.shopmate_app.data.services.networkServices

import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.domain.entities.newtworkEntities.BoardEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BoardService @Inject constructor(private val api: BoardApiClient) {
    suspend fun getBoardsByOwnerId(id: Int): List<BoardEntity>?  {
        return withContext(Dispatchers.IO) {
            val response = api.getBoardsByOwnerId(id, AppConstants.API_KEY)
            response.body()
        }
    }

    suspend fun addBoard(board: BoardEntity): BoardEntity? {
        return withContext(Dispatchers.IO) {
            val response = api.addBoard(board, AppConstants.API_KEY)
            response.body()
        }
    }
}
package com.example.shopmate_app.data.services.networkServices

import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CardService @Inject constructor(private val api: CardApiClient) {
    suspend fun getCardsByBoardId(id: Int): List<CardEntity>?  {
        return withContext(Dispatchers.IO) {
            val response = api.getCardsByBoardId(id, AppConstants.API_KEY)
            response.body()
        }
    }

    suspend fun addCard(boardId: Int, card: CardEntity): CardEntity? {
        return withContext(Dispatchers.IO) {
            val response = api.addCard(boardId, card, AppConstants.API_KEY)
            response.body()
        }
    }
}
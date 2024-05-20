package com.example.shopmate_app.domain.repo

import com.example.shopmate_app.data.services.networkServices.CardService
import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.example.shopmate_app.domain.entities.providers.CardProvider
import javax.inject.Inject

class CardRepository @Inject constructor(private val api: CardService){

    suspend fun getCardsByBoardId(id: Int): List<CardEntity>? {
        return try {
            val response = api.getCardsByBoardId(id)
            if (response != null) {
                CardProvider.cards = response
            }
            response
        } catch (e: Exception) {
            //err
            emptyList<CardEntity>()
        }
    }

    suspend fun addCard(boardId: Int, card: CardEntity): CardEntity? {
        val response = api.addCard(boardId, card)
        CardProvider.card = response
        return response!!
    }

}
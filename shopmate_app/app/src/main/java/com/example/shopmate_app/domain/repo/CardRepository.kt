package com.example.shopmate_app.domain.repo

import com.example.shopmate_app.data.services.networkServices.CardService
import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.example.shopmate_app.domain.entities.providers.CardProvider
import javax.inject.Inject

class CardRepository @Inject constructor(private val api: CardService){

    suspend fun getAllCardsByUserId(id: Int): List<CardEntity> {
        val response = api.getCardsByBoardId(id)
        CardProvider.cards = response
        return response
    }

    suspend fun addCardToABoard(boardId: Int, card: CardEntity): CardEntity {
        val response = api.addCard(boardId, card)
        CardProvider.card = response
        return response!!
    }

    suspend fun getAllCardsByTitle(textToSearch: String): List<CardEntity> {
        val response = api.getCardsByTitle(textToSearch)
        CardProvider.cards = response
        return response!!
    }

}
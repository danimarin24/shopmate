package com.example.shopmate_app.domain.entities.providers

import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity

class CardProvider {
    companion object {
        var cards: List<CardEntity>? = emptyList()

        var card: CardEntity? = null
        var selectedCard: CardEntity? = null
    }
}
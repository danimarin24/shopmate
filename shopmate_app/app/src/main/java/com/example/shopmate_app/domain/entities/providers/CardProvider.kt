package com.example.shopmate_app.domain.entities.providers

import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.CardShareLinkResponseEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.UserRoleEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ValidateShareLinkResponeEntity

class CardProvider {
    companion object {
        var cards: List<CardEntity>? = emptyList()

        var card: CardEntity? = null
        var membersCard: List<UserRoleEntity>? = null
        var categoriesIconsCard: List<String>? = null

        var selectedCard: CardEntity? = null
        var shareCardLink: CardShareLinkResponseEntity? = null
        var validatedToken: ValidateShareLinkResponeEntity? = null
    }
}
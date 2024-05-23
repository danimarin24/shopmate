package com.example.shopmate_app.data.services.networkServices

import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.UserRoleEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ValidateShareLinkRequestEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ValidateShareLinkResponeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CardService @Inject constructor(private val api: CardApiClient) {
    suspend fun getCardsByBoardId(userId: Int): List<CardEntity>  {
        return withContext(Dispatchers.IO) {
            val response = api.getCardsByUserId(userId, AppConstants.API_KEY)
            response.body()!!
        }
    }

    suspend fun addCard(boardId: Int, card: CardEntity): CardEntity? {
        return withContext(Dispatchers.IO) {
            val response = api.addCardToABoard(boardId, card, AppConstants.API_KEY)
            response.body()
        }
    }

    suspend fun getCardsByTitle(textToSearch: String): List<CardEntity>? {
        return withContext(Dispatchers.IO) {
            val response = api.getCardsByTitle(textToSearch, AppConstants.API_KEY)
            response.body()!!
        }
    }

    suspend fun validateCardShareLinkToken(validateEntity: ValidateShareLinkRequestEntity): ValidateShareLinkResponeEntity {
        return withContext(Dispatchers.IO) {
            val response = api.validateShareLink(validateEntity, AppConstants.API_KEY)
            response.body()!!
        }
    }

    suspend fun getMembersFromACard(cardId: Int): List<UserRoleEntity> {
        return withContext(Dispatchers.IO) {
            val response = api.getMembersFromACard(cardId, AppConstants.API_KEY)
            response.body()!!
        }
    }

    suspend fun getCategoriesIcons(cardId: Int): List<String> {
        return withContext(Dispatchers.IO) {
            val response = api.getCategoriesIcons(cardId, AppConstants.API_KEY)
            response.body()!!
        }
    }
}
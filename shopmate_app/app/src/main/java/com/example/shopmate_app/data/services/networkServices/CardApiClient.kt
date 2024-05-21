package com.example.shopmate_app.data.services.networkServices

import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.domain.entities.newtworkEntities.BoardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CardApiClient {
    // GET
    @GET("${AppConstants.CARD_ENDPOINT}users/{userId}/cards")
    suspend fun getCardsByUserId(@Path("userId") userId: Int, @Header("x-api-key") apiKey: String): Response<List<CardEntity>>

    // POST
    @GET("${AppConstants.BOARD_ENDPOINT}/{boardId}/card")
    suspend fun addCardToABoard(@Path("boardId") boardId: Int, @Body card: CardEntity, @Header("x-api-key") apiKey: String): Response<CardEntity>
}
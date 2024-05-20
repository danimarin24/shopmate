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
    @GET("${AppConstants.BOARD_ENDPOINT}user/{id}/card")
    suspend fun getCardsByBoardId(@Path("id") id: Int, @Header("x-api-key") apiKey: String): Response<List<CardEntity>>

    // POST
    @GET("${AppConstants.BOARD_ENDPOINT}user/{id}/card")
    suspend fun addCard(@Path("id") id: Int, @Body card: CardEntity, @Header("x-api-key") apiKey: String): Response<CardEntity>

    // PUT
    @PUT("${AppConstants.CARD_ENDPOINT}{id}")
    suspend fun updateCard(@Path("id") id: Int, @Body user: CardEntity, @Header("x-api-key") apiKey: String): Response<CardEntity>

    // PATCH

    // DELETE
    @PUT("${AppConstants.CARD_ENDPOINT}{id}")
    suspend fun deleteCard(@Path("id") id: Int, @Header("x-api-key") apiKey: String): Response<CardEntity>

}
package com.example.shopmate_app.data.services.networkServices

import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.CardShareLinkRequestEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.CardShareLinkResponseEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ValidateShareLinkRequestEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ValidateShareLinkResponeEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface CardApiClient {
    // GET
    @GET("${AppConstants.CARD_ENDPOINT}users/{userId}/cards")
    suspend fun getCardsByUserId(@Path("userId") userId: Int, @Header("x-api-key") apiKey: String): Response<List<CardEntity>>

    // POST
    @GET("${AppConstants.BOARD_ENDPOINT}{boardId}/card")
    suspend fun addCardToABoard(@Path("boardId") boardId: Int, @Body card: CardEntity, @Header("x-api-key") apiKey: String): Response<CardEntity>

    @POST("${AppConstants.CARD_ENDPOINT}generate-share-link")
    suspend fun generateShareLink(@Body cardShareLinkRequest: CardShareLinkRequestEntity, @Header("x-api-key") apiKey: String): Response<CardShareLinkResponseEntity>

    @POST("${AppConstants.CARD_ENDPOINT}validate-share-link")
    suspend fun validateShareLink(@Body validateShareLinkRequestEntity: ValidateShareLinkRequestEntity, @Header("x-api-key") apiKey: String): Response<ValidateShareLinkResponeEntity>

    @GET("${AppConstants.CARD_ENDPOINT}filter/name/{name}")
    suspend fun getCardsByTitle(@Path("name") name: String,@Header("x-api-key") apiKey: String): Response<List<CardEntity>>

    }

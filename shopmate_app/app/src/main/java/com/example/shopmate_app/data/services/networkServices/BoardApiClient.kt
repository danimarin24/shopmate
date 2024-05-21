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

interface BoardApiClient {
    // GET
    @GET("${AppConstants.BOARD_ENDPOINT}users/{userId}/boards")
    suspend fun getBoardsByUser(@Path("userId") userId: Int, @Header("x-api-key") apiKey: String): Response<List<BoardEntity>>

    @GET("${AppConstants.BOARD_ENDPOINT}{boardId}/cards")
    suspend fun getCardsByBoard(@Path("boardId") boardId: Int, @Header("x-api-key") apiKey: String): Response<List<CardEntity>>


    // POST
    @POST("${AppConstants.BOARD_ENDPOINT}")
    suspend fun addBoard(@Body board: BoardEntity, @Header("x-api-key") apiKey: String): Response<BoardEntity>
}
package com.example.shopmate_app.data.services.networkServices

import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.domain.entities.newtworkEntities.BoardEntity
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
    @GET("${AppConstants.BOARD_ENDPOINT}{id}")
    suspend fun getBoardsByOwnerId(@Path("id") id: Int, @Header("x-api-key") apiKey: String): Response<List<BoardEntity>>

    // POST
    @POST(AppConstants.BOARD_ENDPOINT)
    suspend fun addBoard(@Body user: BoardEntity, @Header("x-api-key") apiKey: String): Response<BoardEntity>

    // PUT
    @PUT(AppConstants.BOARD_ENDPOINT)
    suspend fun updateBoard(@Body user: BoardEntity, @Header("x-api-key") apiKey: String): Response<BoardEntity>

    // PATCH

    // DELETE
    @DELETE(AppConstants.BOARD_ENDPOINT)
    suspend fun deleteBoard(@Body user: BoardEntity, @Header("x-api-key") apiKey: String): Response<BoardEntity>


}
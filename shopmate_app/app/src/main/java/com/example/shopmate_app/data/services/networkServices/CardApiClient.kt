package com.example.shopmate_app.data.services.networkServices

import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.CardShareLinkRequestEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.CardShareLinkResponseEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ItemCardLineEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.UserRoleEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ValidateShareLinkRequestEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ValidateShareLinkResponeEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface CardApiClient {
    // GET
    @GET("${AppConstants.CARD_ENDPOINT}users/{userId}/cards")
    suspend fun getCardsByUserId(@Path("userId") userId: Int, @Header("x-api-key") apiKey: String): Response<List<CardEntity>>

    @GET("${AppConstants.CARD_ENDPOINT}filter/name/{name}")
    suspend fun getCardsByTitle(@Path("name") name: String, @Header("x-api-key") apiKey: String): Response<List<CardEntity>>

    @GET("${AppConstants.CARD_ENDPOINT}{cardId}/members")
    suspend fun getMembersFromACard(@Path("cardId") cardId: Int, @Header("x-api-key") apiKey: String): Response<List<UserRoleEntity>>

    @GET("${AppConstants.CARD_ENDPOINT}{cardId}/items")
    suspend fun getItemsCardLine(@Path("cardId") cardId: Int, @Header("x-api-key") apiKey: String): Response<List<ItemCardLineEntity>>

    @GET("${AppConstants.CARD_ENDPOINT}{cardId}/categoriesIcons")
    suspend fun getCategoriesIcons(@Path("cardId") cardId: Int, @Header("x-api-key") apiKey: String): Response<List<String>>

    // POST
    @POST("${AppConstants.BOARD_ENDPOINT}{boardId}/card")
    suspend fun addCardToABoard(@Path("boardId") boardId: Int, @Body card: CardEntity, @Header("x-api-key") apiKey: String): Response<CardEntity>

    @POST(AppConstants.ITEMCARDLINE_ENDPOINT)
    suspend fun addItemToACard(@Body item: ItemCardLineEntity, @Header("x-api-key") apiKey: String): Response<ItemCardLineEntity>

    @DELETE("${AppConstants.ITEMCARDLINE_ENDPOINT}card/{cardId}/item/{itemId}")
    suspend fun removeItemFromACard(@Path("cardId") cardId: Int, @Path("itemId") itemId: Int, @Header("x-api-key") apiKey: String): Response<ItemCardLineEntity>


    @POST("${AppConstants.CARD_ENDPOINT}generate-share-link")
    suspend fun generateShareLink(@Body cardShareLinkRequest: CardShareLinkRequestEntity, @Header("x-api-key") apiKey: String): Response<CardShareLinkResponseEntity>

    @POST("${AppConstants.CARD_ENDPOINT}validate-share-link")
    suspend fun validateShareLink(@Body validateShareLinkRequestEntity: ValidateShareLinkRequestEntity, @Header("x-api-key") apiKey: String): Response<ValidateShareLinkResponeEntity>
}

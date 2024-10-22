package com.example.shopmate_app.data.services.networkServices

import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.domain.entities.newtworkEntities.UserEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.UserStatsEntity
import com.example.shopmate_app.domain.entities.utilsEntities.UserActionResponseEntity
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface UserApiClient {
    // GET
    @GET(AppConstants.USER_ENDPOINT)
    suspend fun getUsers(@Header("x-api-key") apiKey: String): Response<List<UserEntity>>

    @GET("${AppConstants.USER_ENDPOINT}checkemail/{email}")
    suspend fun getUserByEmail(@Path("email") email: String, @Header("x-api-key") apiKey: String): Response<UserEntity>

    @GET("${AppConstants.USER_ENDPOINT}checkgooglesub/{sub}")
    suspend fun getUserByGoogleSub(@Path("sub") sub: String, @Header("x-api-key") apiKey: String): Response<UserEntity>

    @GET("${AppConstants.USER_ENDPOINT}checkusername/{username}")
    suspend fun getUserByUsername(@Path("username") username: String, @Header("x-api-key") apiKey: String): Response<UserEntity>

    @GET("${AppConstants.USER_ENDPOINT}{id}")
    suspend fun getUserById(@Path("id") id: Int, @Header("x-api-key") apiKey: String): Response<UserEntity>

    @GET("${AppConstants.USER_ENDPOINT}generate/{name}")
    suspend fun getUsernameGenerated(@Path("name") name: String, @Header("x-api-key") apiKey: String): Response<String>

    @GET("${AppConstants.USER_ENDPOINT}stats/{id}")
    suspend fun getUserStats(@Path("id") id: Int, @Header("x-api-key") apiKey: String): Response<UserStatsEntity>

    @GET("${AppConstants.USER_ENDPOINT}{id}/followeds")
    suspend fun getUserFolloweds(@Path("id") id: Int, @Header("x-api-key") apiKey: String): Response<List<UserEntity>>

    @GET("${AppConstants.USER_ENDPOINT}{id}/followers")
    suspend fun getUserFollowers(@Path("id") id: Int, @Header("x-api-key") apiKey: String): Response<List<UserEntity>>

    @GET("${AppConstants.USER_ENDPOINT}{id}/isfollowing/{userActionId}")
    suspend fun isFollowingThisUser(@Path("id") id: Int, @Path("userActionId") userActionId: Int, @Header("x-api-key") apiKey: String): Response<Boolean>

    @GET("${AppConstants.USER_ENDPOINT}filter/username/{username}")
    suspend fun getFilteredUsersByUsername(@Path("username")username: String, @Header("x-api-key") apiKey: String): Response<List<UserEntity>>



    // POST
    @POST("${AppConstants.USER_ENDPOINT}validate-google-token")
    suspend fun validateGoogleToken(
        @Body idTokenString: String,
        @Header("x-api-key") apiKey: String
    ): Response<String>

    @POST(AppConstants.USER_ENDPOINT)
    suspend fun addUser(@Body user: UserEntity, @Header("x-api-key") apiKey: String): Response<UserEntity>

    @Multipart
    @POST("${AppConstants.USER_ENDPOINT}profileImage")
    suspend fun addUserPart(@Part ProfileImage: MultipartBody.Part,
                            @Part("Username") Username: RequestBody,
                            @Part("Name") Name: RequestBody,
                            @Part("Password") Password: RequestBody,
                            @Part("Email") Email: RequestBody,
                            @Part("PhoneNumber") PhoneNumber: RequestBody,
                            @Part("RegisterDate") RegisterDate: RequestBody,
                            @Part("LastConnection") LastConnection: RequestBody,
                            @Part("SettingId") SettingId: RequestBody,
                            @Header("x-api-key") apiKey: String
    ): Response<UserEntity>

    @POST("${AppConstants.USER_ENDPOINT}{id}/action/{userActionId}")
    suspend fun followUnfollowThisUser(@Path("id") id: Int, @Path("userActionId") userActionId: Int, @Header("x-api-key") apiKey: String): Response<UserActionResponseEntity>


    // PUT
    @PUT("${AppConstants.USER_ENDPOINT}{id}")
    suspend fun updateUser(@Path("id") id: Int, @Body user: UserEntity, @Header("x-api-key") apiKey: String): Response<UserEntity>

    // PATCH

    // DELETE
    @DELETE(AppConstants.USER_ENDPOINT)
    suspend fun deleteUser(@Body user: UserEntity, @Header("x-api-key") apiKey: String): Response<UserEntity>




}
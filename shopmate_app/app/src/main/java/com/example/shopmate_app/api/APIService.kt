package com.example.shopmate_app.api

import com.example.shopmate_app.model.Setting
import com.example.shopmate_app.model.SettingId
import com.example.shopmate_app.model.Stat
import com.example.shopmate_app.model.StatId
import com.example.shopmate_app.model.User
import com.example.shopmate_app.model.UserId
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    // USER
    @GET("/api/User/{username}")
    suspend fun getUser(@Path("username") username: String, @Header("x-api-key") apiKey: String): Response<User>

    @GET("/api/User/google/{token}")
    suspend fun getGoogleUser(@Path("token") token: String, @Header("x-api-key") apiKey: String): Response<User>

    @GET("/api/User/{id}")
    suspend fun getUser(@Path("id") id: Int, @Header("x-api-key") apiKey: String): Response<User>

    @GET("/api/User/generate/{name}")
    suspend fun getUsernameGenerated(@Path("name") name: String, @Header("x-api-key") apiKey: String): Response<String>


    @POST("/api/User")
    suspend fun addUser(@Body user: User, @Header("x-api-key") apiKey: String): Response<UserId>


    @Multipart
    @POST("/api/User/profileImage")
    suspend fun addUserPart(@Part ProfileImage: MultipartBody.Part,
                        @Part("Username") Username: RequestBody,
                        @Part("Name") Name: RequestBody,
                        @Part("Password") Password: RequestBody,
                        @Part("Email") Email: RequestBody,
                        @Part("PhoneNumber") PhoneNumber: RequestBody,
                        @Part("RegisterDate") RegisterDate: RequestBody,
                        @Part("LastConnection") LastConnection: RequestBody,
                        @Part("SettingId") SettingId: RequestBody,
                        @Part("StatId") StatId: RequestBody
    ): Response<Message>


    @GET("/api/users")
    suspend fun updateUser(@Body user: User, @Header("x-api-key") apiKey: String): Response<Message>

    @GET("/api/users")
    suspend fun deleteUser(@Body user: User, @Header("x-api-key") apiKey: String): Response<Message>


    // SETTING
    @POST("/api/Setting")
    suspend fun addSetting(@Body setting: Setting, @Header("x-api-key") apiKey: String): Response<SettingId>


    // STAT
    @POST("/api/Stat")
    suspend fun addStat(@Body stat: Stat, @Header("x-api-key") apiKey: String): Response<StatId>

}
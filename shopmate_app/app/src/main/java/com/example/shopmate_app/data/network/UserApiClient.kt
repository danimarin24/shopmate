package com.example.shopmate_app.data.network

import com.example.shopmate_app.data.model.UserModel
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
    @GET("/api/User/")
    suspend fun getUsers(@Header("x-api-key") apiKey: String): Response<List<UserModel>>

    @GET("/api/User/checkemail/{email}")
    suspend fun getUserByEmail(@Path("email") email: String, @Header("x-api-key") apiKey: String): Response<UserModel>

    @GET("/api/User/checkgooglesub/{sub}")
    suspend fun getUserByGoogleSub(@Path("sub") sub: String, @Header("x-api-key") apiKey: String): Response<UserModel>

    @GET("/api/User/checkusername/{username}")
    suspend fun getUserByUsername(@Path("username") username: String, @Header("x-api-key") apiKey: String): Response<UserModel>

    @GET("/api/User/{id}")
    suspend fun getUserById(@Path("id") id: Int, @Header("x-api-key") apiKey: String): Response<UserModel>

    @GET("/api/User/generate/{name}")
    suspend fun getUsernameGenerated(@Path("name") name: String, @Header("x-api-key") apiKey: String): Response<String>

    // POST
    @POST("api/User/validate-google-token")
    suspend fun validateGoogleToken(
        @Body idTokenString: String,
        @Header("x-api-key") apiKey: String
    ): Response<String>

    @POST("/api/User")
    suspend fun addUser(@Body user: UserModel, @Header("x-api-key") apiKey: String): Response<UserModel>

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
    ): Response<UserModel>

    // PUT
    @PUT("/api/users")
    suspend fun updateUser(@Body user: UserModel, @Header("x-api-key") apiKey: String): Response<UserModel>

    // PATCH

    // DELETE
    @DELETE("/api/users")
    suspend fun deleteUser(@Body user: UserModel, @Header("x-api-key") apiKey: String): Response<UserModel>



















}
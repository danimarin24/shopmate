package com.example.shopmate_app.api

import com.example.shopmate_app.model.Categories
import com.example.shopmate_app.model.Category
import com.example.shopmate_app.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    // CATEGORY
    @GET("/api/Categories/{id}")
    suspend fun getCategory(@Path("id") id: Int, @Header("x-api-key") apiKey: String): Response<Category>

    @GET("/api/Categories/")
    suspend fun getCategories(@Header("x-api-key") apiKey: String): Response<Categories>


    // USER
    @GET("/api/User/{username}")
    suspend fun getUser(@Path("username") username: String, @Header("x-api-key") apiKey: String): Response<User>

    @GET("/api/User/{id}")
    suspend fun getUser(@Path("id") id: Int, @Header("x-api-key") apiKey: String): Response<User>


    @GET("/api/users")
    suspend fun addUser(@Body user: User, @Header("x-api-key") apiKey: String): Response<Message>

    @GET("/api/users")
    suspend fun updateUser(@Body user: User, @Header("x-api-key") apiKey: String): Response<Message>

    @GET("/api/users")
    suspend fun deleteUser(@Body user: User, @Header("x-api-key") apiKey: String): Response<Message>

}
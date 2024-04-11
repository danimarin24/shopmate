package com.example.shopmate_app.api

import com.example.shopmate_app.model.Categories
import com.example.shopmate_app.model.Category
import com.example.shopmate_app.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    // CATEGORY
    @GET("/api/Categories/{id}")
    suspend fun getCategory(@Path("id") id: Int): Response<Category>

    @GET("/api/Categories/")
    suspend fun getCategories(): Response<Categories>


    // USER
    @GET("/api/users")
    suspend fun getUser(@Query("token") token: String): Response<User>

    @GET("/api/users")
    suspend fun addUser(@Body user: User): Response<Message>

    @GET("/api/users")
    suspend fun updateUser(@Body user: User): Response<Message>

    @GET("/api/users")
    suspend fun deleteUser(@Body user: User): Response<Message>

}
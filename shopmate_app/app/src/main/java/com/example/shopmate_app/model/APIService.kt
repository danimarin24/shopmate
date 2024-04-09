package com.example.shopmate_app.model

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    // USER
    @GET("/api/user")
    suspend fun getUser(@Query("token") token: String): Response<User>

    @GET("/api/user")
    suspend fun addUser(@Body user: User): Response<Message>

    @GET("/api/user")
    suspend fun updateUser(@Body user: User): Response<Message>

    @GET("/api/user")
    suspend fun deleteUser(@Body user: User): Response<Message>

}
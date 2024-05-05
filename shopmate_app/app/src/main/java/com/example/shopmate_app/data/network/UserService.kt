package com.example.shopmate_app.data.network

import com.example.shopmate_app.core.RetrofitHelper
import com.example.shopmate_app.data.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserService {
    private val retrofit = RetrofitHelper.getRetrofit()
    private val apiKey = RetrofitHelper.getApiKey()

    suspend fun getUsers(): List<UserModel> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(UserApiClient::class.java).getUsers(apiKey)
            response.body() ?: emptyList()
        }
    }
}
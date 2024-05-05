package com.example.shopmate_app.data

import com.example.shopmate_app.data.model.UserModel
import com.example.shopmate_app.data.model.UserProvider
import com.example.shopmate_app.data.network.UserService

class UserRepository {
    private val api = UserService()

    suspend fun getAllUsers(): List<UserModel> {
        val response = api.getUsers()
        UserProvider.users = response
        return response
    }
}
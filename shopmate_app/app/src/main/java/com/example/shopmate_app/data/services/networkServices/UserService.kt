package com.example.shopmate_app.data.services.networkServices

import android.util.Log
import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.domain.entities.newtworkEntities.UserEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.UserStatsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserService @Inject constructor(private val api: UserApiClient) {

    suspend fun getUsers(): List<UserEntity> {
        return withContext(Dispatchers.IO) {
            val response = api.getUsers(AppConstants.API_KEY)
            response.body() ?: emptyList()
        }
    }

    suspend fun getUserStats(id: Int): UserStatsEntity? {
        return withContext(Dispatchers.IO) {
            val response = api.getUserStats(id, AppConstants.API_KEY)
            response.body()
        }
    }

    suspend fun getUserById(id: Int): UserEntity? {
        return withContext(Dispatchers.IO) {
            val response = api.getUserById(id, AppConstants.API_KEY)
            response.body()
        }
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return withContext(Dispatchers.IO) {
            val response = api.getUserByEmail(email, AppConstants.API_KEY)
            response.body()
        }
    }

    suspend fun getUserByUsername(username: String): UserEntity? {
        return withContext(Dispatchers.IO) {
            val response = api.getUserByUsername(username, AppConstants.API_KEY)
            response.body()
        }
    }

    suspend fun getUserByGoogleSub(googleSub: String): UserEntity? {
        return withContext(Dispatchers.IO) {
            val response = api.getUserByGoogleSub(googleSub, AppConstants.API_KEY)
            response.body()
        }
    }

    suspend fun checkGoogleToken(googleToken: String): String? {
        return withContext(Dispatchers.IO) {
            val response = api.validateGoogleToken(googleToken, AppConstants.API_KEY)
            response.body()
        }
    }

    suspend fun getUsernameGenerated(name: String): String? {
        return withContext(Dispatchers.IO) {
            val response = api.getUsernameGenerated(name, AppConstants.API_KEY)
            response.body()
        }
    }

    suspend fun addUser(user: UserEntity): UserEntity? {
        return withContext(Dispatchers.IO) {
            val response = api.addUser(user, AppConstants.API_KEY)
            response.body()
        }
    }

    suspend fun putUser(user: UserEntity): UserEntity? {
        return withContext(Dispatchers.IO) {
            val response = api.updateUser(user.userId!!, user, AppConstants.API_KEY)
            response.body()
        }
    }

    suspend fun getUsersByUsername(textToSearch: String): List<UserEntity> {
        return withContext(Dispatchers.IO) {
            Log.e("getUsersByUsername", "gfadsfasd")
            val response = api.getFilteredUsersByUsername(textToSearch, AppConstants.API_KEY)
            Log.e("dasf", response.body().toString())
            response.body() ?: emptyList()
        }
    }
}



package com.example.shopmate_app.data.services.networkServices

import androidx.core.app.NotificationCompat.MessagingStyle.Message
import com.example.shopmate_app.core.RetrofitHelper
import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.domain.entities.newtworkEntities.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getUsers(): List<UserEntity> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(UserApiClient::class.java).getUsers(AppConstants.API_KEY)
            response.body() ?: emptyList()
        }
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(UserApiClient::class.java).getUserByEmail(email, AppConstants.API_KEY)
            response.body()
        }
    }

    suspend fun getUserByUsername(username: String): UserEntity? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(UserApiClient::class.java).getUserByUsername(username, AppConstants.API_KEY)
            response.body()
        }
    }

    suspend fun getUserByGoogleSub(googleSub: String): UserEntity? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(UserApiClient::class.java).getUserByGoogleSub(googleSub, AppConstants.API_KEY)
            response.body()
        }
    }

    suspend fun checkGoogleToken(googleToken: String): String? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(UserApiClient::class.java).validateGoogleToken(googleToken, AppConstants.API_KEY)
            response.body()
        }
    }

    suspend fun getUsernameGenerated(name: String): String? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(UserApiClient::class.java).getUsernameGenerated(name, AppConstants.API_KEY)
            response.body()
        }
    }


    suspend fun addUser(user: UserEntity): UserEntity? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(UserApiClient::class.java).addUser(user, AppConstants.API_KEY)
            response.body()
        }
    }
}
package com.example.shopmate_app.data.services.networkServices

import android.text.TextUtils
import android.util.Log
import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.domain.entities.newtworkEntities.UserEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.UserStatsEntity
import com.example.shopmate_app.domain.entities.utilsEntities.UserActionResponseEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.w3c.dom.Text
import java.io.File
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

    suspend fun getUsersFolloweds(id: Int): List<UserEntity> {
        return withContext(Dispatchers.IO) {
            val response = api.getUserFolloweds(id, AppConstants.API_KEY)
            response.body() ?: emptyList()
        }
    }

    suspend fun getUsersFollowers(id: Int): List<UserEntity> {
        return withContext(Dispatchers.IO) {
            val response = api.getUserFollowers(id, AppConstants.API_KEY)
            response.body() ?: emptyList()
        }
    }

    suspend fun isFollowingThisUser(id: Int, userActionId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            val response = api.isFollowingThisUser(id, userActionId, AppConstants.API_KEY)
            response.body() ?: false
        }
    }

    suspend fun followUnfollowThisUser(id: Int, userActionId: Int): UserActionResponseEntity {
        return withContext(Dispatchers.IO) {
            val response = api.followUnfollowThisUser(id, userActionId, AppConstants.API_KEY)
            response.body()!!
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

    private fun createPartFromString(stringData: String): RequestBody {
        return stringData.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    suspend fun uploadUserWithImage(user: UserEntity): UserEntity? {
        val newImagePath = user.profileImage ?: return null
        val file = File(newImagePath)
        if (!file.exists()) {
            Log.e("ERROR", "La ruta de este archivo no existe: $newImagePath")
            return null
        }


        val photo: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val bodyImage = MultipartBody.Part.createFormData("profileImage", file.name, photo)

        val username = createPartFromString(user.username)
        val name = createPartFromString(user.name)
        val password = createPartFromString(user.password!!)
        val email = createPartFromString(user.email)
        val phoneNumber = createPartFromString(user.phoneNumber!!)
        val registerDate = createPartFromString(user.registerDate)
        val lastConnection = createPartFromString(user.lastConnection!!)
        val settingId = createPartFromString(user.settingId.toString())

        return withContext(Dispatchers.IO) {
            Log.i("uploadUserWithImage", "test uploading image")
            val response = api.addUserPart(
                bodyImage,
                username,
                name,
                password,
                email,
                phoneNumber,
                registerDate,
                lastConnection,
                settingId
                ,AppConstants.API_KEY)
            response.body()
        }
    }
}



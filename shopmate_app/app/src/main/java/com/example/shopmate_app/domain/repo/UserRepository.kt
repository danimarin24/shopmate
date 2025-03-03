package com.example.shopmate_app.domain.repo

import com.example.shopmate_app.domain.entities.providers.UserProvider
import com.example.shopmate_app.data.services.networkServices.UserService
import com.example.shopmate_app.domain.entities.newtworkEntities.UserEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.UserStatsEntity
import com.example.shopmate_app.domain.entities.providers.UserStatsProvider
import com.example.shopmate_app.domain.entities.utilsEntities.UserActionResponseEntity
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: UserService){
    suspend fun getAllUsers(): List<UserEntity> {
        val response = api.getUsers()
        UserProvider.users = response
        return response
    }

    suspend fun getUserById(id: Int): UserEntity? {
        val response = api.getUserById(id)
        UserProvider.user = response
        return response
    }

    suspend fun getUsersFolloweds(id: Int): List<UserEntity> {
        val response = api.getUsersFolloweds(id)
        UserProvider.usersFolloweds = response
        return response
    }

    suspend fun getUsersFollowers(id: Int): List<UserEntity> {
        val response = api.getUsersFollowers(id)
        UserProvider.usersFollowers = response
        return response
    }

    suspend fun isFollowingThisUser(id: Int, userActionId: Int): Boolean {
        val response = api.isFollowingThisUser(id, userActionId)
        UserProvider.isFollowing = response
        return response
    }

    suspend fun followUnfollowThisUser(id: Int, userActionId: Int): UserActionResponseEntity {
        val response = api.followUnfollowThisUser(id, userActionId)
        UserProvider.responseAction = response
        return response
    }


    suspend fun getUserStats(id: Int): UserStatsEntity? {
        val response = api.getUserStats(id)
        UserStatsProvider.stats = response
        return response
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        val response = api.getUserByEmail(email)
        UserProvider.user = response
        return response
    }

    suspend fun getUserByUsername(username: String): UserEntity? {
        val response = api.getUserByUsername(username)
        UserProvider.user = response
        return response
    }

    suspend fun getUserByGoogleSub(googleSub: String): UserEntity? {
        val response = api.getUserByGoogleSub(googleSub)
        UserProvider.user = response
        return response
    }

    suspend fun getGoogleSubByGoogleToken(googleToken: String): String? {
        val response = api.checkGoogleToken(googleToken)
        UserProvider.googleSub = response
        return response
    }

    suspend fun getUsernameGenerated(name: String): String? {
        val response = api.getUsernameGenerated(name)
        UserProvider.usernameGenerated = response
        return response
    }

    suspend fun addUser(user: UserEntity): UserEntity? {
        val response = api.addUser(user)
        UserProvider.user = response
        return response
    }

    suspend fun addUserWithImage(user: UserEntity): UserEntity? {
        val response = api.uploadUserWithImage(user)
        UserProvider.user = response
        return response
    }

    suspend fun putUser(user: UserEntity): UserEntity? {
        val response = api.putUser(user)
        UserProvider.user = response
        return response

    }

    suspend fun getAllUsersByUsername(textToSearch: String): List<UserEntity> {
        val response = api.getUsersByUsername(textToSearch)
        UserProvider.users = response
        return response
    }
}
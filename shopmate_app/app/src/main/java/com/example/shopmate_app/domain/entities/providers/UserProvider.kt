package com.example.shopmate_app.domain.entities.providers

import com.example.shopmate_app.domain.entities.newtworkEntities.UserEntity
import com.example.shopmate_app.domain.entities.utilsEntities.UserActionResponseEntity

class UserProvider {
    companion object {
        var users: List<UserEntity> = emptyList()
        var usersFolloweds: List<UserEntity> = emptyList()
        var usersFollowers: List<UserEntity> = emptyList()

        var isFollowing: Boolean = false
        var responseAction: UserActionResponseEntity? = null

        var user: UserEntity? = null

        var googleSub: String? = null

        var usernameGenerated: String? = null
    }
}
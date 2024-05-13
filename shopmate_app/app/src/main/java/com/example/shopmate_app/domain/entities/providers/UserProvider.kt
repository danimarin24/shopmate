package com.example.shopmate_app.domain.entities.providers

import com.example.shopmate_app.domain.entities.newtworkEntities.UserEntity

class UserProvider {
    companion object {
        var users: List<UserEntity> = emptyList()

        var user: UserEntity? = null

        var googleSub: String? = null

        var usernameGenerated: String? = null
    }
}
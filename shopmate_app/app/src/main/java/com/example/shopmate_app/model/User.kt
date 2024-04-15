package com.example.shopmate_app.model

import java.sql.Time

data class User(
    // PK
    val id: Int,
    val username: String,
    val password: String,
    val name: String,
    val email: String,
    val phoneNumber: Int,
    val idGoogleToken: String,
    val idFacebookToken: String,
    val profileImage: String,
    val registerDate: Time,
    val lastConnection: Time,
    // FK
    val settingId: Int
)


class Users : ArrayList<User>()
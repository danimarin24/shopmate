package com.example.shopmate_app.model

import java.time.LocalDate

data class User(
    //PK
    var userId: UInt?,
    var username: String,
    var name: String,
    var password: String?,
    var email: String,
    var phoneNumber: String,
    var profileImage: String,
    var googleToken: String?,
    var facebookToken: String?,
    var registerDate: String,
    var lastConnection: String?,
    //FK
    var settingId: UInt,
    var statId: UInt
)

data class UserId (
    val userId: String
)

class Users : ArrayList<User>()
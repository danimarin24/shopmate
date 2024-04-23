package com.example.shopmate_app.model

import java.time.LocalDateTime

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
    var registerDate: LocalDateTime,
    var lastConnection: LocalDateTime?,
    //FK
    var settingId: UInt,
    var statId: UInt
)

class Users : ArrayList<User>()
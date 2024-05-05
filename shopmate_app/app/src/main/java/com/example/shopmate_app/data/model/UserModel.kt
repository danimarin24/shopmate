package com.example.shopmate_app.data.model

data class UserModel(
    //PK
    var userId: Int?,
    var username: String,
    var name: String,
    var password: String?,
    var email: String,
    var phoneNumber: String?,
    var profileImage: String,
    var googleToken: String?,
    var facebookToken: String?,
    var registerDate: String,
    var lastConnection: String?,
    //FK
    var settingId: Int,
    var statId: Int
)
package com.example.shopmate_app.domain.entities.newtworkEntities

data class UserEntity(
    //PK
    var userId: Int?,
    var username: String,
    var name: String,
    var password: String?,
    var email: String,
    var phoneNumber: String?,
    var profileImage: String?,
    var googleToken: String?,
    var facebookToken: String?,
    var registerDate: String,
    var lastConnection: String?,
    //FK
    var settingId: Int,
)
package com.example.shopmate_app.model.api

data class Setting(
    val settingId: UInt?,
    val lastConnection: String,
    val isOnline: ULong,
    val isAdmin: ULong,
    val isPrivate: ULong,
    val isInfluencer: ULong,
    val lastPasswordChanged: String,
    val lastPasswordHash: String
)

data class SettingId(
    val settingId: String
)

class Settings : ArrayList<Setting>()
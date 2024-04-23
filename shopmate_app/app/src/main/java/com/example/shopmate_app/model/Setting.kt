package com.example.shopmate_app.model

import java.time.LocalDateTime

data class Setting (
    val settingId : UInt?,
    val lastConnection : LocalDateTime,
    val isOnline : ULong,
    val isAdmin : ULong,
    val isPrivate : ULong,
    val isInfluencer : ULong,
    val lastPasswordChanged : LocalDateTime,
    val lastPasswordHash : String
)

class Settings : ArrayList<Setting>()
package com.example.shopmate_app.domain.entities.providers

import com.example.shopmate_app.domain.entities.newtworkEntities.UserStatsEntity

class UserStatsProvider {
    companion object {
        var stats: UserStatsEntity? = null
    }
}
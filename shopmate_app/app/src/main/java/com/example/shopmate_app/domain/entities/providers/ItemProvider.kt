package com.example.shopmate_app.domain.entities.providers

import com.example.shopmate_app.domain.entities.newtworkEntities.ItemCardLineEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ItemEntity

class ItemProvider {
    companion object {
        var items: List<ItemCardLineEntity> = emptyList()

        var item: ItemCardLineEntity? = null
        var lastItemModified: ItemCardLineEntity? = null
        var lastItemRemoved: ItemCardLineEntity? = null
        var selectedItem: ItemCardLineEntity? = null
    }
}
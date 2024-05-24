package com.example.shopmate_app.domain.entities.newtworkEntities

data class ItemCardLineEntity(
    val itemCardLineId: Int,
    val cardId: Int,
    val createdBy: String,
    val assignedTo: String,
    val amount: Int,
    val item: ItemEntity,
    val price: Int,
    val unit: UnitEntity
)
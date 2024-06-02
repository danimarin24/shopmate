package com.example.shopmate_app.domain.entities.newtworkEntities

data class ItemCardLineEntity(
    val itemCardLineId: Int,
    val cardId: Int,
    val createdBy: Int,
    val assignedTo: Int,
    val assignedToUser: UserEntity? = null,
    val amount: Int,
    val item: ItemEntity? = null,
    val itemId: Int,
    val price: Float,
    val unit: UnitEntity? = null,
    val unitId: Int,
)
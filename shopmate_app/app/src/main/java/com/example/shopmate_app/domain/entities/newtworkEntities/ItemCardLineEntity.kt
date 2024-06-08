package com.example.shopmate_app.domain.entities.newtworkEntities

data class ItemCardLineEntity(
    val itemCardLineId: Int,
    val cardId: Int,
    val createdBy: Int,
    var assignedTo: Int,
    val assignedToUser: UserEntity? = null,
    var amount: Int,
    val item: ItemEntity? = null,
    val itemId: Int,
    var price: Float,
    var unit: UnitEntity? = null,
    var unitId: Int,
)
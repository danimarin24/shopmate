package com.example.shopmate_app.domain.usecases.item

import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ItemCardLineEntity
import com.example.shopmate_app.domain.repo.CardRepository
import javax.inject.Inject

class AddItemLineToACardUseCase @Inject constructor(private val repository: CardRepository){
    suspend operator fun invoke(cardId: Int, itemCardLine: ItemCardLineEntity): ItemCardLineEntity = repository.addItemToACard(cardId, itemCardLine)
}
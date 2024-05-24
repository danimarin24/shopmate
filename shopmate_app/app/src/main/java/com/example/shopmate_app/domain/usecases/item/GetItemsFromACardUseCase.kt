package com.example.shopmate_app.domain.usecases.item

import com.example.shopmate_app.domain.entities.newtworkEntities.ItemCardLineEntity
import com.example.shopmate_app.domain.repo.CardRepository
import javax.inject.Inject

class GetItemsFromACardUseCase @Inject constructor(private val repository: CardRepository) {
    suspend operator fun invoke(cardId: Int) : List<ItemCardLineEntity> = repository.getItemsCardLine(cardId)
}
package com.example.shopmate_app.domain.usecases.item

import com.example.shopmate_app.domain.entities.newtworkEntities.ItemCardLineEntity
import com.example.shopmate_app.domain.repo.CardRepository
import javax.inject.Inject

class ModifyItemLineFromACardUseCase @Inject constructor(private val repository: CardRepository){
    suspend operator fun invoke(itemId: Int, item: ItemCardLineEntity) = repository.modifyItemFromACard(itemId, item)
}
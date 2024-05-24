package com.example.shopmate_app.domain.usecases.card

import com.example.shopmate_app.domain.entities.newtworkEntities.UserRoleEntity
import com.example.shopmate_app.domain.repo.CardRepository
import javax.inject.Inject

class GetCategoriesIconsUseCase @Inject constructor(private val repository: CardRepository) {
    suspend operator fun invoke(cardId: Int) : List<String> = repository.getCategoriesIcons(cardId)
}
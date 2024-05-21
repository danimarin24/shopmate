package com.example.shopmate_app.domain.usecases.card

import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.example.shopmate_app.domain.repo.CardRepository
import javax.inject.Inject

class GetCardsByUserUseCase @Inject constructor(
    private val repository: CardRepository
) {
    suspend operator fun invoke(userId: Int): List<CardEntity> {
        return repository.getAllCardsByUserId(userId)
    }
}
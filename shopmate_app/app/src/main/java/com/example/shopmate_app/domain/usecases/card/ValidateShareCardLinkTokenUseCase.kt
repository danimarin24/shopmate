package com.example.shopmate_app.domain.usecases.card

import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ValidateShareLinkRequestEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ValidateShareLinkResponeEntity
import com.example.shopmate_app.domain.repo.CardRepository
import javax.inject.Inject

class ValidateShareCardLinkTokenUseCase @Inject constructor(private val repository: CardRepository) {
    suspend operator fun invoke(validateEntity: ValidateShareLinkRequestEntity) : ValidateShareLinkResponeEntity = repository.validateCardShareLinkToken(validateEntity)
}
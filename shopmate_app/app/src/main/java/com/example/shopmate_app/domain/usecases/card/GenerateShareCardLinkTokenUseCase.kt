package com.example.shopmate_app.domain.usecases.card

import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.CardShareLinkRequestEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.CardShareLinkResponseEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ValidateShareLinkRequestEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ValidateShareLinkResponeEntity
import com.example.shopmate_app.domain.repo.CardRepository
import javax.inject.Inject

class GenerateShareCardLinkTokenUseCase @Inject constructor(private val repository: CardRepository) {
    suspend operator fun invoke(shareReq: CardShareLinkRequestEntity) : CardShareLinkResponseEntity = repository.generateCardShareLinkToken(shareReq)
}
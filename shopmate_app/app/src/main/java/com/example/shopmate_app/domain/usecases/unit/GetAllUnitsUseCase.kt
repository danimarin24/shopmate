package com.example.shopmate_app.domain.usecases.unit

import com.example.shopmate_app.domain.repo.UnitRepository
import javax.inject.Inject

class GetAllUnitsUseCase @Inject constructor(private val repository: UnitRepository){
    suspend operator fun invoke() = repository.getAllUnits()
}
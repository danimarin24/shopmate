package com.example.shopmate_app.domain.usecases.color

import com.example.shopmate_app.domain.repo.ColorRepository
import javax.inject.Inject

class GetAllColorsUseCase @Inject constructor(private val repository: ColorRepository){
    suspend operator fun invoke() = repository.getAllColors()
}
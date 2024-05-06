package com.example.shopmate_app.domain.usecases.user

import com.example.shopmate_app.domain.repo.UserRepository

class GetUsernameGeneratedUseCase {
    suspend operator fun invoke(name: String) = UserRepository.getUsernameGenerated(name)
}
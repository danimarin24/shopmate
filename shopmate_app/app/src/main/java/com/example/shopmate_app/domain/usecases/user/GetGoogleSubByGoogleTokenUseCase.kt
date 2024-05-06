package com.example.shopmate_app.domain.usecases.user

import com.example.shopmate_app.domain.repo.UserRepository

class GetGoogleSubByGoogleTokenUseCase {
    suspend operator fun invoke(googleToken: String) = UserRepository.getGoogleSubByGoogleToken(googleToken)
}
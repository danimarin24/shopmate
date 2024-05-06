package com.example.shopmate_app.domain.usecases.user

import com.example.shopmate_app.domain.repo.UserRepository

class GetUserByGoogleSubUseCase {
    suspend operator fun invoke(googleSub: String) = UserRepository.getUserByGoogleSub(googleSub)
}
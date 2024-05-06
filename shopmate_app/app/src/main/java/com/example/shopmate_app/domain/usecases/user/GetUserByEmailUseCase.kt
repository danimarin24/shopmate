package com.example.shopmate_app.domain.usecases.user

import com.example.shopmate_app.domain.repo.UserRepository

class GetUserByEmailUseCase {
    suspend operator fun invoke(email: String) = UserRepository.getUserByEmail(email)
}
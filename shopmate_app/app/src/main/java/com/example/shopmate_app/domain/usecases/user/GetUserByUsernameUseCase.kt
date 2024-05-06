package com.example.shopmate_app.domain.usecases.user

import com.example.shopmate_app.domain.repo.UserRepository

class GetUserByUsernameUseCase {
    suspend operator fun invoke(username: String) = UserRepository.getUserByUsername(username)
}
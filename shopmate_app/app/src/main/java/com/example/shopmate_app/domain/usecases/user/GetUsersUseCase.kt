package com.example.shopmate_app.domain.usecases.user

import com.example.shopmate_app.domain.repo.UserRepository

class GetUsersUseCase {
    suspend operator fun invoke() = UserRepository.getAllUsers()
}
package com.example.shopmate_app.domain

import com.example.shopmate_app.data.UserRepository

class GetUsersUseCase {
    private val repository = UserRepository()

    suspend operator fun invoke() = repository.getAllUsers()
}
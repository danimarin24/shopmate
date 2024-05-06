package com.example.shopmate_app.domain.usecases.user

import com.example.shopmate_app.domain.entities.newtworkEntities.UserEntity
import com.example.shopmate_app.domain.repo.UserRepository

class AddUserUseCase {
    suspend operator fun invoke(user: UserEntity) = UserRepository.addUser(user)
}
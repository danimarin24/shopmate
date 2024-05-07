package com.example.shopmate_app.domain.usecases.user

import com.example.shopmate_app.domain.entities.newtworkEntities.UserEntity
import com.example.shopmate_app.domain.repo.UserRepository
import javax.inject.Inject

class AddUserUseCase @Inject constructor(private val repository: UserRepository){
    suspend operator fun invoke(user: UserEntity) = repository.addUser(user)
}
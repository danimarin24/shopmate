package com.example.shopmate_app.domain.usecases.user

import com.example.shopmate_app.domain.repo.UserRepository
import javax.inject.Inject

class GetUserByUsernameUseCase @Inject constructor(private val repository: UserRepository){
    suspend operator fun invoke(username: String) = repository.getUserByUsername(username)
}
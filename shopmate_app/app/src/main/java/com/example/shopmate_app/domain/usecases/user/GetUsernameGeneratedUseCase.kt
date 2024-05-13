package com.example.shopmate_app.domain.usecases.user

import com.example.shopmate_app.domain.repo.UserRepository
import javax.inject.Inject

class GetUsernameGeneratedUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(name: String) = repository.getUsernameGenerated(name)
}
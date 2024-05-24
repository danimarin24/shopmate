package com.example.shopmate_app.domain.usecases.user

import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.UserEntity
import com.example.shopmate_app.domain.repo.UserRepository
import javax.inject.Inject

class GetUsersByUsernameUseCase @Inject constructor( private val repository: UserRepository) {
    suspend operator fun invoke(textToSearch: String) : List<UserEntity> = repository.getAllUsersByUsername(textToSearch)

}

package com.example.shopmate_app.domain.usecases.board

import com.example.shopmate_app.domain.repo.BoardRepository
import javax.inject.Inject

class GetBoardsByOwnerIdUseCase @Inject constructor(private val repository: BoardRepository){
    suspend operator fun invoke(id: Int) = repository.getBoardsByOwnerId(id)
}
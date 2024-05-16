package com.example.shopmate_app.domain.usecases.board

import com.example.shopmate_app.domain.entities.newtworkEntities.BoardEntity
import com.example.shopmate_app.domain.repo.BoardRepository
import javax.inject.Inject

class AddBoardUseCase @Inject constructor(private val repository: BoardRepository){
    suspend operator fun invoke(board: BoardEntity) = repository.addBoard(board)
}
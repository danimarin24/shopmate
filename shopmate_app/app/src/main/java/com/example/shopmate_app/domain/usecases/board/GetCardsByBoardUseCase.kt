package com.example.shopmate_app.domain.usecases.board

import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.example.shopmate_app.domain.repo.BoardRepository
import javax.inject.Inject

class GetCardsByBoardUseCase @Inject constructor(private val repository: BoardRepository) {
    suspend operator fun invoke(boardId: Int): List<CardEntity> {
        return repository.getCardsByBoard(boardId)
    }
}
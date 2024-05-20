package com.example.shopmate_app.domain.usecases.board

import com.example.shopmate_app.domain.entities.dbEntities.DbBoardEntity
import com.example.shopmate_app.domain.repo.BoardRepository
import com.example.shopmate_app.domain.repo.CardRepository
import javax.inject.Inject

class GetBoardsWithCardsByOwnerIdUseCase @Inject constructor(private val boardRepository: BoardRepository, private val cardRepository: CardRepository){
    suspend operator fun invoke(id: Int): List<DbBoardEntity>? {
        var localBoardData: MutableList<DbBoardEntity>? = mutableListOf()
        var boards = boardRepository.getBoardsByOwnerId(id)
        boards?.forEach {
            var cards = cardRepository.getCardsByBoardId(id) ?: emptyList()
            var dbBoard = DbBoardEntity(it.boardId, it.title, it.ownerId, cards)
            localBoardData?.add(dbBoard)
        }
        return localBoardData
    }
}
package com.example.shopmate_app.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopmate_app.domain.entities.newtworkEntities.BoardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.example.shopmate_app.domain.usecases.board.AddBoardUseCase
import com.example.shopmate_app.domain.usecases.board.GetBoardsByUserUseCase
import com.example.shopmate_app.domain.usecases.board.GetCardsByBoardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val getBoardsByUserUseCase: GetBoardsByUserUseCase,
    private val getCardsByBoardUseCase: GetCardsByBoardUseCase,
    private val addBoardUseCase: AddBoardUseCase

) : ViewModel() {
    private val _boards = MutableLiveData<List<BoardEntity>>()
    val boards: LiveData<List<BoardEntity>> get() = _boards

    private val _cardsByBoard = MutableLiveData<Map<Int, List<CardEntity>>>()
    val cardsByBoard: LiveData<Map<Int, List<CardEntity>>> get() = _cardsByBoard

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    fun fetchBoards(userId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val boards = getBoardsByUserUseCase(userId)
                _boards.value = boards

                // Fetch cards for each board
                boards.forEach { board ->
                    fetchCards(board.boardId)
                }
            } catch (e: Exception) {
                // Handle error
                _isError.value = true
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun fetchCards(boardId: Int) {
        viewModelScope.launch {
            try {
                val cards = getCardsByBoardUseCase(boardId)
                val currentMap = _cardsByBoard.value ?: emptyMap()
                _cardsByBoard.value = currentMap + (boardId to cards)
            } catch (e: Exception) {
                // Handle error
                _isError.value = true
                _isLoading.value = false
            }
        }
    }

    fun addBoard(board: BoardEntity) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val newBoard = addBoardUseCase(board)
                val updatedBoards = _boards.value.orEmpty().toMutableList().apply { add(newBoard) }
                _boards.value = updatedBoards
            } catch (e: Exception) {
                // Handle error
                _isError.value = true
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}
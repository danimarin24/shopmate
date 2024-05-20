package com.example.shopmate_app.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopmate_app.domain.entities.dbEntities.DbBoardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.BoardEntity
import com.example.shopmate_app.domain.usecases.board.AddBoardUseCase
import com.example.shopmate_app.domain.usecases.board.GetBoardsByOwnerIdUseCase
import com.example.shopmate_app.domain.usecases.board.GetBoardsWithCardsByOwnerIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val getBoardsByOwnerIdUseCase: GetBoardsByOwnerIdUseCase,
    private val getBoardsWithCardsByOwnerIdUseCase: GetBoardsWithCardsByOwnerIdUseCase,
    private val addBoardUseCase : AddBoardUseCase,
) : ViewModel() {

    val boardsEntity = MutableLiveData<List<BoardEntity>?>()
    val boardsCardsEntity = MutableLiveData<List<DbBoardEntity>?>()
    val boardEntity = MutableLiveData<BoardEntity?>()
    val isLoading = MutableLiveData<Boolean>()


    fun getBoardsByOwnerId(id: Int) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getBoardsByOwnerIdUseCase(id)

            if(result != null){
                boardsEntity.postValue(result)
                isLoading.postValue(false)
            }
        }
    }

    fun getBoardWithCardsByOwnerId(id: Int) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getBoardsWithCardsByOwnerIdUseCase(id)

            if(result != null){
                boardsCardsEntity.postValue(result)
                isLoading.postValue(false)
            }
        }
    }

    fun addBoard(board: BoardEntity) {
        viewModelScope.launch {
            val result = addBoardUseCase(board)
            if(result != null){
                boardEntity.postValue(result)
            }
        }
    }
}
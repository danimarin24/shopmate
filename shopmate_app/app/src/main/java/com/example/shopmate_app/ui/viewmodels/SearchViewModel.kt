package com.example.shopmate_app.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopmate_app.domain.entities.newtworkEntities.BoardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.UserEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.UserStatsEntity
import com.example.shopmate_app.domain.usecases.card.GetCardByTitleUseCase
import com.example.shopmate_app.domain.usecases.user.GetUsersByUsernameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getCardByTitle: GetCardByTitleUseCase,
    private val getUsersByUsername: GetUsersByUsernameUseCase


) : ViewModel() {


    private val _cards = MutableLiveData<List<CardEntity>>()
    private val _users = MutableLiveData<List<UserEntity>>()


    //  val userEntity = MutableLiveData<UserEntity?>()
    val isLoading = MutableLiveData<Boolean>()
    val isError = MutableLiveData<Boolean>()
    val searchCardStatsEntity = MutableLiveData<List<CardEntity>>()
    val searchUsersStatsEntity = MutableLiveData<List<UserEntity>>()

    fun searchCards(textString: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getCardByTitle(textString)

            if (result != null) {
                searchCardStatsEntity.postValue(result)
                isLoading.postValue(false)

            }
        }
    }

    fun searchUsers(searchText: String) {
        isLoading.postValue(true)
        viewModelScope.launch {
            try {
                val result = getUsersByUsername(searchText)
                if (result != null) {
                    searchUsersStatsEntity.postValue(result)
                    isError.postValue(false)
                } else {
                    isError.postValue(true)
                }
            } catch (e: Exception) {
                isError.postValue(true)
            } finally {
                isLoading.postValue(false)
            }
        }
    }
}




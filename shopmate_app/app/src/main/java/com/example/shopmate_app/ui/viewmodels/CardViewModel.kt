package com.example.shopmate_app.ui.viewmodels

import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.UserRoleEntity
import com.example.shopmate_app.domain.usecases.card.AddCardToABoardUseCase
import com.example.shopmate_app.domain.usecases.card.GetCardsByUserUseCase
import com.example.shopmate_app.domain.usecases.card.GetCategoriesIconsUseCase
import com.example.shopmate_app.domain.usecases.card.GetMembersFromACardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val getAllCardsByUserUseCase: GetCardsByUserUseCase,
    private val addCardToABoardUseCase: AddCardToABoardUseCase,
    private val getCategoriesIconsUseCase: GetCategoriesIconsUseCase,
    private val getMembersFromACardUseCase: GetMembersFromACardUseCase

) : ViewModel() {
    private val _cards = MutableLiveData<List<CardEntity>>()
    val cards: LiveData<List<CardEntity>> get() = _cards

    private val _members = MutableLiveData<List<UserRoleEntity>>()
    val members: LiveData<List<UserRoleEntity>> get() = _members

    private val _categoriesIcons = MutableLiveData<List<String>>()
    val categoriesIcons: LiveData<List<String>> get() = _categoriesIcons

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    fun fetchAllCards(userId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val cards = getAllCardsByUserUseCase(userId)
                _cards.value = cards
            } catch (e: Exception) {
                // Handle error
                _isError.value = true
                _isLoading.value = false
            }
        }
    }

    fun addCardToABoard(boardId: Int, card: CardEntity) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val newCard = addCardToABoardUseCase(boardId, card)
                val updatedCards = _cards.value.orEmpty().toMutableList().apply { add(newCard) }
                Log.e("updatedCardscardviewmodel", updatedCards.toString())
                _cards.value = updatedCards
            } catch (e: Exception) {
                // Handle error
                _isError.value = true
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchCategoriesIconsByCard(cardId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val categoriesIcons = getCategoriesIconsUseCase(cardId)
                _categoriesIcons.value = categoriesIcons
            } catch (e: Exception) {
                // Handle error
                _isError.value = true
                _isLoading.value = false
            }
        }
    }

    fun fetchMembersByCard(cardId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val members = getMembersFromACardUseCase(cardId)
                _members.value = members
            } catch (e: Exception) {
                // Handle error
                _isError.value = true
                _isLoading.value = false
            }
        }
    }
}
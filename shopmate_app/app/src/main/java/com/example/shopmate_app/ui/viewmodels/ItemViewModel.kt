package com.example.shopmate_app.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ItemCardLineEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.UserRoleEntity
import com.example.shopmate_app.domain.usecases.card.AddCardToABoardUseCase
import com.example.shopmate_app.domain.usecases.card.GetCardsByUserUseCase
import com.example.shopmate_app.domain.usecases.card.GetCategoriesIconsUseCase
import com.example.shopmate_app.domain.usecases.card.GetMembersFromACardUseCase
import com.example.shopmate_app.domain.usecases.item.AddItemLineToACardUseCase
import com.example.shopmate_app.domain.usecases.item.GetItemsFromACardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val getItemsFromACardUseCase: GetItemsFromACardUseCase,
    private val addItemLineToACard: AddItemLineToACardUseCase,

) : ViewModel() {
    private val _items = MutableLiveData<List<ItemCardLineEntity>>()
    val items: LiveData<List<ItemCardLineEntity>> get() = _items

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    fun fetchAllItems(cardId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val items = getItemsFromACardUseCase(cardId)
                _items.value = items
            } catch (e: Exception) {
                // Handle error
                _isError.value = true
                _isLoading.value = false
            }
        }
    }

    fun addItemToACard(cardId: Int, itemCardLineEntity: ItemCardLineEntity) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val newItem = addItemLineToACard(cardId, itemCardLineEntity)
                val updatedCards = _items.value.orEmpty().toMutableList().apply { add(newItem) }
                _items.value = updatedCards
            } catch (e: Exception) {
                // Handle error
                _isError.value = true
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun removeItem(item: ItemCardLineEntity) {
        _items.value = _items.value?.filter { it != item }
    }

    fun assignItem(item: ItemCardLineEntity) {
        // Lógica para asignar el item
    }

    fun unassignItem(item: ItemCardLineEntity) {
        // Lógica para desasignar el item
    }
}
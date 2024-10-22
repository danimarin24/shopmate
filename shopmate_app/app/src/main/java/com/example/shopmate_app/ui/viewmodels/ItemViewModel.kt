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
import com.example.shopmate_app.domain.usecases.item.ModifyItemLineFromACardUseCase
import com.example.shopmate_app.domain.usecases.item.RemoveItemLineFromACardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val getItemsFromACardUseCase: GetItemsFromACardUseCase,
    private val addItemLineToACard: AddItemLineToACardUseCase,
    private val removeItemLineFromACardUseCase: RemoveItemLineFromACardUseCase,
    private val modifyItemLineFromACardUseCase: ModifyItemLineFromACardUseCase

) : ViewModel() {
    private val _items = MutableLiveData<List<ItemCardLineEntity>>()
    val items: LiveData<List<ItemCardLineEntity>> get() = _items

    private val _lastRemovedItem = MutableLiveData<ItemCardLineEntity>()
    val lastRemovedItem: LiveData<ItemCardLineEntity> get() = _lastRemovedItem

    private val _lastItemClicked = MutableLiveData<ItemCardLineEntity>()
    val lastItemClicked: LiveData<ItemCardLineEntity> get() = _lastItemClicked

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

    fun addItemToACard(itemCardLineEntity: ItemCardLineEntity) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val newItem = addItemLineToACard(itemCardLineEntity)
                fetchAllItems(newItem.cardId)
                _lastItemClicked.value = newItem
            } catch (e: Exception) {
                // Handle error
                _isError.value = true
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun removeItemFromACard(item: ItemCardLineEntity) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val removedItem = removeItemLineFromACardUseCase(item.cardId, item.itemId)
                fetchAllItems(item.cardId)
                _lastRemovedItem.value = removedItem
            } catch (e: Exception) {
                // Handle error
                _isError.value = true
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun modifyItemFromACard(item: ItemCardLineEntity) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val modifiedItem = modifyItemLineFromACardUseCase(item.itemCardLineId, item)
            } catch (e: Exception) {
                // Handle error
                _isError.value = true
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun assignItem(item: ItemCardLineEntity, userToAssingId: Int) {
        // Lógica para asignar el item
        item.assignedTo = userToAssingId
        modifyItemFromACard(item)
    }

    fun unassignItem(item: ItemCardLineEntity, beforeAssignedUserId: Int) {
        // Lógica para desasignar el item
        item.assignedTo = beforeAssignedUserId
        modifyItemFromACard(item)
    }
}
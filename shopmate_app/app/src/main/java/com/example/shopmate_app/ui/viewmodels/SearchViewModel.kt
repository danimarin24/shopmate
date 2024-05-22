package com.example.shopmate_app.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopmate_app.domain.entities.newtworkEntities.BoardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.UserEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.UserStatsEntity
import com.example.shopmate_app.domain.usecases.card.GetCardByTitleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getCardByTitle: GetCardByTitleUseCase


) : ViewModel() {


    private val _cards = MutableLiveData<List<CardEntity>>()

    //  val userEntity = MutableLiveData<UserEntity?>()
    val isLoading = MutableLiveData<Boolean>()
    val isError = MutableLiveData<Boolean>()
    val searchCardStatsEntity = MutableLiveData<List<CardEntity>>()

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

}


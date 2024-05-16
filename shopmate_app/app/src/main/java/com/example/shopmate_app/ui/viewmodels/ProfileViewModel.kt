package com.example.shopmate_app.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopmate_app.domain.entities.newtworkEntities.UserEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.UserStatsEntity
import com.example.shopmate_app.domain.usecases.user.GetUserByIdUseCase
import com.example.shopmate_app.domain.usecases.user.GetUserStatsByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserStatsByIdUseCase: GetUserStatsByIdUseCase,
    private val getUserById: GetUserByIdUseCase,

    ) : ViewModel() {

    val userStatsEntity = MutableLiveData<UserStatsEntity?>()
    val userEntity = MutableLiveData<UserEntity?>()
    val isLoading = MutableLiveData<Boolean>()

    fun getUserStats(id: Int) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getUserStatsByIdUseCase(id)

            if(result != null){
                userStatsEntity.postValue(result)
                isLoading.postValue(false)
            }
        }
    }

    fun getUserInformation(id: Int) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getUserById(id)

            if(result != null){
                userEntity.postValue(result)
                isLoading.postValue(false)
            }
        }
    }


}
package com.example.shopmate_app.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopmate_app.data.model.UserModel
import com.example.shopmate_app.domain.GetUsersUseCase
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    val userModel = MutableLiveData<List<UserModel>>()
    val isLoading = MutableLiveData<Boolean>()

    var getUsersUseCase = GetUsersUseCase()

    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getUsersUseCase()

            if(result.isNotEmpty()){
                userModel.postValue(result)
                isLoading.postValue(false)
            }
        }
    }
}
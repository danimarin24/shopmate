package com.example.shopmate_app.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.domain.entities.newtworkEntities.UserEntity
import com.example.shopmate_app.domain.usecases.user.AddUserUseCase
import com.example.shopmate_app.domain.usecases.user.GetGoogleSubByGoogleTokenUseCase
import com.example.shopmate_app.domain.usecases.user.GetUserByEmailUseCase
import com.example.shopmate_app.domain.usecases.user.GetUserByGoogleSubUseCase
import com.example.shopmate_app.domain.usecases.user.GetUserByUsernameUseCase
import com.example.shopmate_app.domain.usecases.user.GetUsernameGeneratedUseCase
import com.example.shopmate_app.domain.usecases.user.GetUsersUseCase
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    val usersEntity = MutableLiveData<List<UserEntity>>()
    val userEntity = MutableLiveData<UserEntity?>()
    val usernameGenerated = MutableLiveData<String?>()
    val googleSub = MutableLiveData<String?>()
    val isLoading = MutableLiveData<Boolean>()

    // usecases
    var getGoogleSubByGoogleTokenUseCase = GetGoogleSubByGoogleTokenUseCase()
    var getUsernameGeneratedUseCase = GetUsernameGeneratedUseCase()
    var getUsersUseCase = GetUsersUseCase()
    var getUserByEmailUseCase = GetUserByEmailUseCase()
    var getUserByUsernameUseCase = GetUserByUsernameUseCase()
    var getUserByGoogleSubUseCase = GetUserByGoogleSubUseCase()
    var addUserUseCase = AddUserUseCase()

    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getUsersUseCase()

            if(result.isNotEmpty()){
                usersEntity.postValue(result)
                isLoading.postValue(false)
            }
        }
    }

    fun checkUserEmail(email: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getUserByEmailUseCase(email)

            if(result != null){
                userEntity.postValue(result)
                isLoading.postValue(false)
            }
        }
    }

    fun getGoogleSub(googleToken: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getGoogleSubByGoogleTokenUseCase(googleToken)

            if(result != null){
                googleSub.postValue(result)
                isLoading.postValue(false)
            }
        }
    }

    fun getUserByGoogleSub(googleSub: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getUserByGoogleSubUseCase(googleSub)

            if(result != null){
                userEntity.postValue(result)
                isLoading.postValue(false)
            }
        }
    }

    fun getUserByUsername(username: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getUserByUsernameUseCase(username)

            if(result != null){
                userEntity.postValue(result)
                isLoading.postValue(false)
            }
        }
    }

    fun generateUsername(name: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getUsernameGeneratedUseCase(name)

            if(result != null){
                usernameGenerated.postValue(result)
                isLoading.postValue(false)
            }
        }
    }
}
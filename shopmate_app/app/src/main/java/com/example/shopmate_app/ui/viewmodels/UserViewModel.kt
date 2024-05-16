package com.example.shopmate_app.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopmate_app.domain.entities.newtworkEntities.UserEntity
import com.example.shopmate_app.domain.usecases.user.AddUserUseCase
import com.example.shopmate_app.domain.usecases.user.GetGoogleSubByGoogleTokenUseCase
import com.example.shopmate_app.domain.usecases.user.GetUserByEmailUseCase
import com.example.shopmate_app.domain.usecases.user.GetUserByGoogleSubUseCase
import com.example.shopmate_app.domain.usecases.user.GetUserByUsernameUseCase
import com.example.shopmate_app.domain.usecases.user.GetUsernameGeneratedUseCase
import com.example.shopmate_app.domain.usecases.user.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getGoogleSubByGoogleTokenUseCase : GetGoogleSubByGoogleTokenUseCase,
    private val getUsernameGeneratedUseCase : GetUsernameGeneratedUseCase,
    private val getUserByEmailUseCase : GetUserByEmailUseCase,
    private val getUserByUsernameUseCase : GetUserByUsernameUseCase,
    private val getUserByGoogleSubUseCase : GetUserByGoogleSubUseCase,
    private val addUserUseCase : AddUserUseCase,
) : ViewModel() {

    val userEntity = MutableLiveData<UserEntity?>()
    val usernameGenerated = MutableLiveData<String?>()
    val googleSub = MutableLiveData<String?>()
    val isLoading = MutableLiveData<Boolean>()

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

    fun getUserByEmail(email: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getUserByEmailUseCase(email)

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

    fun addUser(user: UserEntity) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = addUserUseCase(user)

            if(result != null){
                userEntity.postValue(result)
                isLoading.postValue(false)
            }
        }
    }
}
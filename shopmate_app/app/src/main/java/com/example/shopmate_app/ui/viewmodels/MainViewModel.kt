package com.example.shopmate_app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.data.implementation.DataStoreRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: DataStoreRepositoryImpl
) : ViewModel() {
    fun saveUserToken(value: String) {
        viewModelScope.launch {
            repository.putString(AppConstants.USER_ID, value)
        }
    }

    fun getUserToken(): String? = runBlocking {
        repository.getString(AppConstants.USER_ID)
    }

    fun saveUserId(value: Int) {
        viewModelScope.launch {
            repository.putInt(AppConstants.USER_ID, value)
        }
    }

    fun getUserId(): Int? = runBlocking {
        repository.getInt(AppConstants.USER_ID)
    }
}
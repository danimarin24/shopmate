package com.example.shopmate_app.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.shopmate_app.controller.DataStoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel (application: Application) : AndroidViewModel(application) {

    private val dataStore = DataStoreManager(application)

    val getTheme = dataStore.getTheme().asLiveData(Dispatchers.IO)
    val getUserId = dataStore.getUserId().asLiveData(Dispatchers.IO)

    fun setTheme(isDarkMode : Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.setTheme(isDarkMode)
        }
    }

    fun setUserId(userId : Int) {
        Log.i("guardo idfdskjfhahjsdfas", userId.toString())
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.setUserId(userId)
        }
    }

}
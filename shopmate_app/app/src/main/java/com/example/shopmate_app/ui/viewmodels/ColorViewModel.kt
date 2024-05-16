package com.example.shopmate_app.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopmate_app.domain.entities.newtworkEntities.ColorEntity
import com.example.shopmate_app.domain.usecases.color.GetAllColorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ColorViewModel @Inject constructor(
    private val getAllColorsUseCase: GetAllColorsUseCase,
    ) : ViewModel() {
    val colorList = MutableLiveData<List<ColorEntity>?>()

    fun getColors() {
        viewModelScope.launch {
            val result = getAllColorsUseCase()
            colorList.postValue(result)
        }
    }
}
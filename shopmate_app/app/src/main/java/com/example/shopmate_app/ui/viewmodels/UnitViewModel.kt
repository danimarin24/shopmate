package com.example.shopmate_app.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopmate_app.domain.entities.newtworkEntities.UnitEntity
import com.example.shopmate_app.domain.usecases.unit.GetAllUnitsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UnitViewModel @Inject constructor(
    private val getAllUnitsUseCase: GetAllUnitsUseCase,
    ) : ViewModel() {
    private val _units = MutableLiveData<List<UnitEntity>>()
    val units: LiveData<List<UnitEntity>> get() = _units

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    fun getUnits() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val boards = getAllUnitsUseCase()
                _units.value = boards
            } catch (e: Exception) {
                // Handle error
                _isError.value = true
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}
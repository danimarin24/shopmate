package com.example.shopmate_app.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ValidateShareLinkRequestEntity
import com.example.shopmate_app.domain.usecases.card.ValidateShareCardLinkTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
    private var validateShareCardLinkTokenUseCase: ValidateShareCardLinkTokenUseCase
) : ViewModel() {
    private val _tokenValidation = MutableLiveData<Boolean>()
    val tokenValidation: LiveData<Boolean> get() = _tokenValidation

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError


    fun validateCardShareLinkToken(validateEntity: ValidateShareLinkRequestEntity) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = validateShareCardLinkTokenUseCase(validateEntity)
                _tokenValidation.value = response.isValid
            } catch (e: Exception) {
                _tokenValidation.value = false
                _isError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }
}
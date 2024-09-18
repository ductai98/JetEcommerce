package com.taild.jetecommerce.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taild.domain.exception.BusinessException
import com.taild.domain.model.Product
import com.taild.domain.usecase.GetProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase
) : ViewModel() {
    private var _uiState = MutableStateFlow<HomeScreenUIState>(HomeScreenUIState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch {
            _uiState.value = HomeScreenUIState.Loading
            val result = getProductUseCase()
            if (result.isSuccess) {
                _uiState.value = HomeScreenUIState.Success(result.getOrNull() ?: emptyList())
            } else {
                val exception = result.exceptionOrNull()
                if (exception is BusinessException) {
                    _uiState.value = HomeScreenUIState.Error(exception.message ?: "Unknown error")
                } else {
                    _uiState.value = HomeScreenUIState.Error("Unknown error")
                }
            }
        }
    }
}

sealed interface HomeScreenUIState {
    data object Loading : HomeScreenUIState
    data class Success(val data: List<Product>) : HomeScreenUIState
    data class Error(val message: String) : HomeScreenUIState
}
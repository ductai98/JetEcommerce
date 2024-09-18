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
        getAllProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            _uiState.value = HomeScreenUIState.Loading
            val feature = getProducts("electronics")
            val popular = getProducts("jewelery")
            if (feature.isEmpty() || popular.isEmpty()) {
                _uiState.value = HomeScreenUIState.Error("Fail to load products")
                return@launch
            }
            _uiState.value = HomeScreenUIState.Success(feature, popular)
        }
    }

    private suspend fun getProducts(category: String?) : List<Product> {
        val result = getProductUseCase(category)
        if (result.isSuccess) {
            return result.getOrNull() ?: emptyList()
        } else {
            val exception = result.exceptionOrNull()
            if (exception is BusinessException) {
                return emptyList()
            } else {
                return emptyList()
            }
        }
    }
}

sealed interface HomeScreenUIState {
    data object Loading : HomeScreenUIState
    data class Success(val feature: List<Product>, val popular: List<Product>) : HomeScreenUIState
    data class Error(val message: String) : HomeScreenUIState
}
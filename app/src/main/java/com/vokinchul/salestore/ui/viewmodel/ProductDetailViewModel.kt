package com.vokinchul.salestore.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.vokinchul.salestore.domain.model.Product
import com.vokinchul.salestore.domain.usecase.GetProductUseCase
import com.vokinchul.salestore.domain.usecase.UpdateProductInCartUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.launch

class ProductDetailViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val updateProductInCartUseCase: UpdateProductInCartUseCase
) : ViewModel() {

    private val _productState = mutableStateOf<Product?>(null)
    val productState: State<Product?> get() = _productState

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> get() = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> get() = _error

    fun loadProduct(productId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _productState.value = getProductUseCase(productId)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateCartStatus(productId: Int, isInCart: Boolean) {
        viewModelScope.launch {
            try {
                updateProductInCartUseCase(productId, isInCart)
                // Обновляем локальное состояние
                _productState.value = _productState.value?.copy(isInCart = isInCart)
            } catch (e: Exception) {
                // Обработка ошибки
                _error.value = "Ошибка обновления корзины: ${e.message}"
            }
        }
    }
}
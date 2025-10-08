package com.vokinchul.salestore.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vokinchul.salestore.domain.model.Product
import com.vokinchul.salestore.domain.usecase.ClearCartUseCase
import com.vokinchul.salestore.domain.usecase.GetCartProductsUseCase
import com.vokinchul.salestore.domain.usecase.UpdateProductInCartUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShoppingCartViewModel @Inject constructor(
    private val getCartProductsUseCase: GetCartProductsUseCase,
    private val updateProductInCartUseCase: UpdateProductInCartUseCase,
    private val clearCartUseCase: ClearCartUseCase
) : ViewModel() {

    private val _cartProducts = mutableStateOf<List<Product>>(emptyList())
    val cartProducts: State<List<Product>> get() = _cartProducts

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> get() = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> get() = _error

    fun loadCartProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _cartProducts.value = getCartProductsUseCase()
            } catch (e: Exception) {
                _error.value = "Ошибка загрузки корзины: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun removeFromCart(productId: Int) {
        viewModelScope.launch {
            try {
                updateProductInCartUseCase(productId, false)
                // Обновляем локальный список
                _cartProducts.value = _cartProducts.value.filter { it.id != productId }
            } catch (e: Exception) {
                _error.value = "Ошибка удаления товара: ${e.message}"
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            try {
                clearCartUseCase()
                _cartProducts.value = emptyList()
            } catch (e: Exception) {
                _error.value = "Ошибка очистки корзины: ${e.message}"
            }
        }
    }
}
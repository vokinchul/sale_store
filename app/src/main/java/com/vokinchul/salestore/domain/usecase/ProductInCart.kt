package com.vokinchul.salestore.domain.usecase

import com.vokinchul.salestore.domain.model.Product
import com.vokinchul.salestore.domain.repository.FakeStoreRepository
import jakarta.inject.Inject

class UpdateProductInCartUseCase @Inject constructor(
    private val repository: FakeStoreRepository
) {
    suspend operator fun invoke(productId: Int, isInCart: Boolean) {
        repository.updateProductInCart(productId, isInCart)
    }
}

class GetCartProductsUseCase @Inject constructor(
    private val repository: FakeStoreRepository
) {
    suspend operator fun invoke(): List<Product> {
        return repository.getCartProducts()
    }
}

class ClearCartUseCase @Inject constructor(
    private val repository: FakeStoreRepository
) {
    suspend operator fun invoke() {
        repository.clearCart()
    }
}
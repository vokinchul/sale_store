package com.vokinchul.salestore.domain.usecase

import com.vokinchul.salestore.domain.model.Cart
import com.vokinchul.salestore.domain.repository.FakeStoreRepository
import jakarta.inject.Inject

class GetCartUseCase @Inject constructor(
    private val repository: FakeStoreRepository
) {
    suspend operator fun invoke(id: Int): Cart {
        return repository.getCart(id)
    }
}

class AddCartUseCase @Inject constructor(
    private val repository: FakeStoreRepository
) {
    suspend operator fun invoke(cart: Cart): Cart {
        return repository.addCart(cart)
    }
}
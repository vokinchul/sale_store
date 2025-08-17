package com.vokinchul.salestore.domain.usecase

import com.vokinchul.salestore.domain.model.Product
import com.vokinchul.salestore.domain.repository.FakeStoreRepository
import jakarta.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: FakeStoreRepository
) {
    suspend operator fun invoke(limit: Int? = null, sort: String? = null): List<Product> {
        return repository.getProducts(limit, sort)
    }
}

class GetProductUseCase @Inject constructor(
    private val repository: FakeStoreRepository
) {
    suspend operator fun invoke(id: Int): Product {
        return repository.getProduct(id)
    }
}

class GetProductsByCategoryUseCase @Inject constructor(
    private val repository: FakeStoreRepository
) {
    suspend operator fun invoke(category: String, sort: String? = null): List<Product> {
        return repository.getProductsByCategory(category, sort)
    }
}

class GetCategoriesUseCase @Inject constructor(
    private val repository: FakeStoreRepository
) {
    suspend operator fun invoke(): List<String> {
        return repository.getCategories()
    }
}
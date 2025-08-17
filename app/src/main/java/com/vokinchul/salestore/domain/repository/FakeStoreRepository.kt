package com.vokinchul.salestore.domain.repository

import com.vokinchul.salestore.domain.model.AuthToken
import com.vokinchul.salestore.domain.model.Cart
import com.vokinchul.salestore.domain.model.Product
import com.vokinchul.salestore.domain.model.User

interface FakeStoreRepository {
    suspend fun getProducts(limit: Int? = null, sort: String? = null): List<Product>
    suspend fun getProduct(id: Int): Product
    suspend fun getProductsByCategory(category: String, sort: String? = null): List<Product>
    suspend fun getCategories(): List<String>

    suspend fun addCart(cart: Cart): Cart
    suspend fun getCart(id: Int): Cart

    suspend fun getUser(id: Int): User

    suspend fun login(username: String, password: String): AuthToken
}
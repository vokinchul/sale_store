package com.vokinchul.salestore.data.repository

import com.vokinchul.salestore.data.api.FakeStoreApi
import com.vokinchul.salestore.data.api.LoginRequest
import com.vokinchul.salestore.domain.model.AuthToken
import com.vokinchul.salestore.domain.model.Cart
import com.vokinchul.salestore.domain.model.Product
import com.vokinchul.salestore.domain.model.User
import com.vokinchul.salestore.domain.repository.FakeStoreRepository
import jakarta.inject.Inject

class FakeStoreRepositoryImpl @Inject constructor(
    private val api: FakeStoreApi
) : FakeStoreRepository {

    override suspend fun getProducts(limit: Int?, sort: String?): List<Product> {
        return try {
            api.getProducts(limit, sort) // Предполагается, что FakeStoreApi имеет такой метод
        } catch (e: Exception) {
            throw Exception("Ошибка при получении продуктов: ${e.message}")
        }
    }

    override suspend fun getProduct(id: Int): Product {
        return try {
            api.getProduct(id) // Вызов API для получения продукта по ID
        } catch (e: Exception) {
            throw Exception("Ошибка при получении продукта с ID $id: ${e.message}")
        }
    }

    override suspend fun getProductsByCategory(category: String, sort: String?): List<Product> {
        return try {
            api.getProductsByCategory(category, sort) // Вызов API для продуктов по категории
        } catch (e: Exception) {
            throw Exception("Ошибка при получении продуктов категории $category: ${e.message}")
        }
    }

    override suspend fun getCategories(): List<String> {
        return try {
            api.getCategories() // Получение категорий
        } catch (e: Exception) {
            throw Exception("Ошибка при получении категорий: ${e.message}")
        }
    }

    override suspend fun addCart(cart: Cart): Cart {
        return try {
            api.addCart(cart) // Симуляция добавления корзины
        } catch (e: Exception) {
            throw Exception("Ошибка при добавлении корзины: ${e.message}")
        }
    }

    override suspend fun getCart(id: Int): Cart {
        return try {
            api.getCart(id) // Получение корзины по ID
        } catch (e: Exception) {
            throw Exception("Ошибка при получении корзины с ID $id: ${e.message}")
        }
    }

    override suspend fun getUser(id: Int): User {
        return try {
            api.getUser(id) // Получение пользователя по ID
        } catch (e: Exception) {
            throw Exception("Ошибка при получении пользователя с ID $id: ${e.message}")
        }
    }

    override suspend fun login(username: String, password: String): AuthToken {
        return try {
            api.login(LoginRequest(username, password)) // Получение JWT-токена
        } catch (e: Exception) {
            throw Exception("Ошибка при авторизации: ${e.message}")
        }
    }
}
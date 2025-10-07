package com.vokinchul.salestore.data.repository

import com.vokinchul.salestore.data.api.FakeStoreApi
import com.vokinchul.salestore.data.api.LoginRequest
import com.vokinchul.salestore.data.dao.ProductDao
import com.vokinchul.salestore.data.mapper.toEntityList
import com.vokinchul.salestore.data.mapper.toProduct
import com.vokinchul.salestore.data.mapper.toProductList
import com.vokinchul.salestore.domain.model.AuthToken
import com.vokinchul.salestore.domain.model.Cart
import com.vokinchul.salestore.domain.model.Product
import com.vokinchul.salestore.domain.model.User
import com.vokinchul.salestore.domain.repository.FakeStoreRepository
import jakarta.inject.Inject

class FakeStoreRepositoryImpl @Inject constructor(
    private val api: FakeStoreApi,
    private val productDao: ProductDao
) : FakeStoreRepository {

    override suspend fun getProducts(limit: Int?, sort: String?): List<Product> {
        return try {
            // Пытаемся получить из сети
            val products = api.getProducts(limit, sort)
            // Сохраняем в кэш
            productDao.insertAll(products.toEntityList())
            products
        } catch (e: Exception) {
            // Если ошибка - пробуем взять из кэша
            val cachedProducts = productDao.getAllProducts()
            if (cachedProducts.isNotEmpty()) {
                cachedProducts.toProductList()
            } else {
                throw Exception("Ошибка при получении продуктов: ${e.message}")
            }
        }
    }

    override suspend fun getProduct(id: Int): Product {
        return try {
            val product = api.getProduct(id)
            // Проверяем статус корзины из БД
            val cachedProduct = productDao.getProductById(id)
            product.copy(isInCart = cachedProduct?.isInCart ?: false)
        } catch (e: Exception) {
            val cachedProduct = productDao.getProductById(id)
            cachedProduct?.toProduct()
                ?: throw Exception("Ошибка при получении продукта с ID $id: ${e.message}")
        }
    }

    override suspend fun getProductsByCategory(category: String, sort: String?): List<Product> {
        return try {
            api.getProductsByCategory(category, sort)
        } catch (e: Exception) {
            throw Exception("Ошибка при получении продуктов категории $category: ${e.message}")
        }
    }

    override suspend fun getCategories(): List<String> {
        return try {
            api.getCategories()
        } catch (e: Exception) {
            throw Exception("Ошибка при получении категорий: ${e.message}")
        }
    }

    override suspend fun addCart(cart: Cart): Cart {
        return try {
            api.addCart(cart)
        } catch (e: Exception) {
            throw Exception("Ошибка при добавлении корзины: ${e.message}")
        }
    }

    override suspend fun getCart(id: Int): Cart {
        return try {
            api.getCart(id)
        } catch (e: Exception) {
            throw Exception("Ошибка при получении корзины с ID $id: ${e.message}")
        }
    }

    override suspend fun getUser(id: Int): User {
        return try {
            api.getUser(id)
        } catch (e: Exception) {
            throw Exception("Ошибка при получении пользователя с ID $id: ${e.message}")
        }
    }

    override suspend fun login(username: String, password: String): AuthToken {
        return try {
            api.login(LoginRequest(username, password))
        } catch (e: Exception) {
            throw Exception("Ошибка при авторизации: ${e.message}")
        }
    }

    override suspend fun updateProductInCart(productId: Int, isInCart: Boolean) {
        productDao.updateCartStatus(productId, isInCart)
    }

    override suspend fun getCartProducts(): List<Product> {
        return productDao.getCartProducts().toProductList()
    }

    override suspend fun clearCart() {
        val cartProducts = productDao.getCartProducts()
        cartProducts.forEach { product ->
            productDao.updateCartStatus(product.id, false)
        }
    }
}
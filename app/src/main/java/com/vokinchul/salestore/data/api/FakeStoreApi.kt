package com.vokinchul.salestore.data.api

import com.vokinchul.salestore.domain.model.AuthToken
import com.vokinchul.salestore.domain.model.Cart
import com.vokinchul.salestore.domain.model.Product
import com.vokinchul.salestore.domain.model.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface FakeStoreApi {
    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int? = null,
        @Query("sort") sort: String? = null
    ): List<Product>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): Product

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(
        @Path("category") category: String,
        @Query("sort") sort: String? = null
    ): List<Product>

    @GET("products/categories")
    suspend fun getCategories(): List<String>

    @GET("carts/{id}")
    suspend fun getCart(@Path("id") id: Int): Cart

    @POST("carts")
    suspend fun addCart(@Body cart: Cart): Cart

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): User

    @POST("auth/login")
    suspend fun login(@Body credentials: LoginRequest): AuthToken
}

data class LoginRequest(val username: String, val password: String)
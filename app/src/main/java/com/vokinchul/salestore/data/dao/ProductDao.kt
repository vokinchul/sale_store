package com.vokinchul.salestore.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vokinchul.salestore.data.model.ProductEntity

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)

    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: Int): ProductEntity?

    @Update
    suspend fun updateProduct(product: ProductEntity)

    @Query("UPDATE products SET isInCart = :isInCart WHERE id = :productId")
    suspend fun updateCartStatus(productId: Int, isInCart: Boolean)

    @Query("SELECT * FROM products WHERE isInCart = 1")
    suspend fun getCartProducts(): List<ProductEntity>
}
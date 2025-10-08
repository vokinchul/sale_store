package com.vokinchul.salestore.data.mapper

import com.vokinchul.salestore.data.model.ProductEntity
import com.vokinchul.salestore.domain.model.Product
import com.vokinchul.salestore.domain.model.Rating

//  API  → Entity
fun Product.toEntity(): ProductEntity = ProductEntity(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    rate = rating.rate,
    count = rating.count,
    isInCart = isInCart
)

// Entity → Domain
fun ProductEntity.toProduct(): Product = Product(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    rating = Rating(rate = rate, count = count),
    isInCart = isInCart
)

// Для списков
fun List<Product>.toEntityList(): List<ProductEntity> = map { it.toEntity() }
fun List<ProductEntity>.toProductList(): List<Product> = map { it.toProduct() }
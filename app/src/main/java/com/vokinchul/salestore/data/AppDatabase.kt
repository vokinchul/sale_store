package com.vokinchul.salestore.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vokinchul.salestore.data.dao.ProductDao
import com.vokinchul.salestore.data.model.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
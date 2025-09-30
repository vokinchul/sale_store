package com.vokinchul.salestore.di

import android.content.Context
import androidx.room.Room
import com.vokinchul.salestore.data.AppDatabase
import com.vokinchul.salestore.data.dao.ProductDao
import dagger.Module
import dagger.Provides
import jakarta.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "products_db"
        ).fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideProductDao(appDatabase: AppDatabase): ProductDao {
        return appDatabase.productDao()
    }
}
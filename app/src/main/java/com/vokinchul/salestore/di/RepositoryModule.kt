package com.vokinchul.salestore.di

import com.vokinchul.salestore.data.repository.FakeStoreRepositoryImpl
import com.vokinchul.salestore.domain.repository.FakeStoreRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindFakeStoreRepository(
        impl: FakeStoreRepositoryImpl
    ): FakeStoreRepository
}
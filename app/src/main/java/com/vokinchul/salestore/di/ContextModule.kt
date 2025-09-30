package com.vokinchul.salestore.di

import android.content.Context
import dagger.Module
import dagger.Provides
import jakarta.inject.Singleton

@Module
class ContextModule(private val context: Context) {

    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext(): Context = context

}
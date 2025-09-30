package com.vokinchul.salestore.di

import com.vokinchul.MyApp
import com.vokinchul.salestore.MainActivity
import com.vokinchul.salestore.di.NetworkModule
import dagger.Component
import jakarta.inject.Singleton

@Singleton
@Component(modules = [
    ContextModule::class,
    NetworkModule::class,
    DatabaseModule::class,
    RepositoryModule::class
])
interface AppComponent {
    fun inject(app: MyApp)
    fun inject(activity: MainActivity)
}
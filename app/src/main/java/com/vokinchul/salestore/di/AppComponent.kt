package com.vokinchul.salestore.di

import com.vokinchul.MyApp
import com.vokinchul.salestore.MainActivity
import dagger.Component
import jakarta.inject.Singleton

@Singleton
@Component(modules = [
    ContextModule::class,
    NetworkModule::class,
    DatabaseModule::class,
    RepositoryModule::class,
    ViewModelModule::class
])
interface AppComponent {
    fun inject(app: MyApp)
    fun inject(activity: MainActivity)

    fun viewModelsFactory(): ViewModelFactory

}
package com.vokinchul

import android.app.Application
import com.vokinchul.salestore.di.AppComponent
import com.vokinchul.salestore.di.ContextModule
import com.vokinchul.salestore.di.DaggerAppComponent


class MyApp : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }
}
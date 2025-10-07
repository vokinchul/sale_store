package com.vokinchul.salestore.di

import androidx.lifecycle.ViewModel
import com.vokinchul.salestore.ui.viewmodel.ProductDetailViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProductDetailViewModel::class)
    fun bindProductDetailViewModel(viewModel: ProductDetailViewModel): androidx.lifecycle.ViewModel
}


@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
package com.oguzhandongul.shoppingapp.features.cart.di

import com.oguzhandongul.shoppingapp.features.cart.data.CartRepositoryImpl
import com.oguzhandongul.shoppingapp.product.local.CartDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CartModule {
    @Singleton
    @Provides
    fun provideProductsRepo(cartDao: CartDao) =
        CartRepositoryImpl(cartDao = cartDao)
}
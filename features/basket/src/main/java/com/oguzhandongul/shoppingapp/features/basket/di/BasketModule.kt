package com.oguzhandongul.shoppingapp.features.basket.di

import com.oguzhandongul.shoppingapp.features.basket.data.BasketRepositoryImpl
import com.oguzhandongul.shoppingapp.product.local.BasketDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BasketModule {
    @Singleton
    @Provides
    fun provideProductsRepo(basketDao: BasketDao) =
        BasketRepositoryImpl(basketDao = basketDao)
}
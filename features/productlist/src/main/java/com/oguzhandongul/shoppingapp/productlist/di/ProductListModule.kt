package com.oguzhandongul.shoppingapp.productlist.di

import com.oguzhandongul.shoppingapp.core.util.utils.JsonFileParser
import com.oguzhandongul.shoppingapp.product.local.ProductDao
import com.oguzhandongul.shoppingapp.productlist.data.ProductListRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductListModule {
    @Singleton
    @Provides
    fun provideProductsRepo(jsonFileParser: JsonFileParser, productDao: ProductDao) =
        ProductListRepositoryImpl(jsonFileParser = jsonFileParser, productDao = productDao)
}
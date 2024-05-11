package com.oguzhandongul.shoppingapp.di

import com.oguzhandongul.shoppingapp.product.repository.ProductRepository
import com.oguzhandongul.shoppingapp.productlist.data.ProductListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindProductRepository(productListRepositoryImpl: ProductListRepositoryImpl): ProductRepository
}

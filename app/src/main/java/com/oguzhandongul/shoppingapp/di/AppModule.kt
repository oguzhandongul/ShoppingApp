package com.oguzhandongul.shoppingapp.di

import android.content.Context
import com.oguzhandongul.shoppingapp.features.cart.data.CartRepositoryImpl
import com.oguzhandongul.shoppingapp.product.local.AppDatabase
import com.oguzhandongul.shoppingapp.product.local.CartDao
import com.oguzhandongul.shoppingapp.product.local.ProductDao
import com.oguzhandongul.shoppingapp.product.repository.CartRepository
import com.oguzhandongul.shoppingapp.product.repository.ProductRepository
import com.oguzhandongul.shoppingapp.productlist.data.ProductListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    companion object {
        @Provides
        @Singleton
        fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
            return AppDatabase.getDatabase(context)
        }

        @Provides
        fun provideProductDao(appDatabase: AppDatabase): ProductDao {
            return appDatabase.productDao()
        }
        @Provides
        fun provideCartDao(appDatabase: AppDatabase): CartDao {
            return appDatabase.cartDao()
        }
    }
    @Binds
    abstract fun bindProductRepository(productListRepositoryImpl: ProductListRepositoryImpl): ProductRepository
    @Binds
    abstract fun bindCartRepository(cartRepositoryImpl: CartRepositoryImpl): CartRepository
}

package com.oguzhandongul.shoppingapp.product.repository

import com.oguzhandongul.shoppingapp.product.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProductList(): Flow<List<Product>>
    suspend fun cacheProductList(): Result<Unit>
}
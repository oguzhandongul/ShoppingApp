package com.oguzhandongul.shoppingapp.product.repository

import com.oguzhandongul.shoppingapp.product.model.Product

interface ProductRepository {
    suspend fun getProductList(): Result<List<Product>>
}
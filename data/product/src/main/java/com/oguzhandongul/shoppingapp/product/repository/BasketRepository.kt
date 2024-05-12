package com.oguzhandongul.shoppingapp.product.repository

import com.oguzhandongul.shoppingapp.product.model.BasketItem
import com.oguzhandongul.shoppingapp.product.model.Product
import kotlinx.coroutines.flow.Flow

interface BasketRepository {
    fun getBasketItems(): Flow<List<BasketItem>>
    suspend fun addToBasket(product: Product)
    suspend fun removeFromBasket(productId: String)
    suspend fun updateBasketItemQuantity(basketItem: BasketItem)
    suspend fun clearBasket()
}
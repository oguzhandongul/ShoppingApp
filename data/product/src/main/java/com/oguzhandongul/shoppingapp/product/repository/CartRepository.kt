package com.oguzhandongul.shoppingapp.product.repository

import com.oguzhandongul.shoppingapp.product.model.CartItem
import com.oguzhandongul.shoppingapp.product.model.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(): Flow<List<CartItem>>
    suspend fun addToCart(product: Product)
    suspend fun removeFromCart(productId: String)
    suspend fun updateCartItemQuantity(cartItem: CartItem)
    suspend fun emptyCart()
}
package com.oguzhandongul.shoppingapp.features.cart.data

import com.oguzhandongul.shoppingapp.product.local.CartDao
import com.oguzhandongul.shoppingapp.product.model.CartItem
import com.oguzhandongul.shoppingapp.product.model.Product
import com.oguzhandongul.shoppingapp.product.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(private val cartDao: CartDao) :
    CartRepository {
    override fun getCartItems(): Flow<List<CartItem>> = cartDao.getAllCartItems()

    override suspend fun addToCart(product: Product) {
        val existingCartItem = cartDao.getCartItemByProductId(product.id)
        if (existingCartItem != null) {
            // Update existing item's quantity
            cartDao.updateCartItem(existingCartItem.copy(quantity = existingCartItem.quantity + 1))
        } else {
            // Insert a new cart item with the product details
            cartDao.insertCartItem(CartItem(productId = product.id, quantity = 1, product = product))
        }
    }

    override suspend fun removeFromCart(productId: String) {
        val cartItem = cartDao.getCartItemByProductId(productId)
        if (cartItem != null) {
            cartDao.deleteCartItem(cartItem)
        }
    }

    override suspend fun updateCartItemQuantity(cartItem: CartItem) {
        cartDao.updateCartItem(cartItem)
    }

    override suspend fun emptyCart() {
        cartDao.deleteAllCartItems()
    }
}
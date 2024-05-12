package com.oguzhandongul.shoppingapp.features.cart.domain

import com.oguzhandongul.shoppingapp.product.model.CartItem
import com.oguzhandongul.shoppingapp.product.repository.CartRepository
import javax.inject.Inject

class UpdateCartItemQuantityUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend operator fun invoke(cartItem: CartItem) = cartRepository.updateCartItemQuantity(cartItem)
}
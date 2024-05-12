package com.oguzhandongul.shoppingapp.features.cart.domain

import com.oguzhandongul.shoppingapp.product.repository.CartRepository
import javax.inject.Inject

class RemoveFromCartUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend operator fun invoke(productId: String) = cartRepository.removeFromCart(productId)
}
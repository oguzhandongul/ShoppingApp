package com.oguzhandongul.shoppingapp.features.cart.domain

import com.oguzhandongul.shoppingapp.product.repository.CartRepository
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(private val cartRepository: CartRepository) {
    operator fun invoke() = cartRepository.getCartItems()
}
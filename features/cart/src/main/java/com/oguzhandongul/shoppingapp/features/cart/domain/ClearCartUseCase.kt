package com.oguzhandongul.shoppingapp.features.cart.domain

import com.oguzhandongul.shoppingapp.product.repository.CartRepository
import javax.inject.Inject

class ClearCartUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend operator fun invoke() = cartRepository.emptyCart()
}
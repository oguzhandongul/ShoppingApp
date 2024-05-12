package com.oguzhandongul.shoppingapp.productlist.domain.usecase

import com.oguzhandongul.shoppingapp.product.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCartItemCountUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke(): Flow<Int> = cartRepository.getCartItems()
        .map { cartItems ->
            cartItems.sumOf { it.quantity } // Calculate the total quantity
        }
}
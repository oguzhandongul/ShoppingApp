package com.oguzhandongul.shoppingapp.productlist.domain.usecase

import com.oguzhandongul.shoppingapp.product.repository.BasketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBasketItemCountUseCase @Inject constructor(
    private val basketRepository: BasketRepository
) {
    operator fun invoke(): Flow<Int> = basketRepository.getBasketItems()
        .map { basketItems ->
            basketItems.sumOf { it.quantity } // Calculate the total quantity
        }
}
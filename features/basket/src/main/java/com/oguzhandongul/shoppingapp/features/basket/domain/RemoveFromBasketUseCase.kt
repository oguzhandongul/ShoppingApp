package com.oguzhandongul.shoppingapp.features.basket.domain

import com.oguzhandongul.shoppingapp.product.repository.BasketRepository
import javax.inject.Inject

class RemoveFromBasketUseCase @Inject constructor(private val basketRepository: BasketRepository) {
    suspend operator fun invoke(productId: String) = basketRepository.removeFromBasket(productId)
}
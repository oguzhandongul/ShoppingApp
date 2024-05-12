package com.oguzhandongul.shoppingapp.features.basket.domain

import com.oguzhandongul.shoppingapp.product.repository.BasketRepository
import javax.inject.Inject

class GetBasketItemsUseCase @Inject constructor(private val basketRepository: BasketRepository) {
    operator fun invoke() = basketRepository.getBasketItems()
}
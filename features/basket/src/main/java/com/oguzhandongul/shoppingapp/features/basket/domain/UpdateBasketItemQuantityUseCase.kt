package com.oguzhandongul.shoppingapp.features.basket.domain

import com.oguzhandongul.shoppingapp.product.model.BasketItem
import com.oguzhandongul.shoppingapp.product.repository.BasketRepository
import javax.inject.Inject

class UpdateBasketItemQuantityUseCase @Inject constructor(private val basketRepository: BasketRepository) {
    suspend operator fun invoke(basketItem: BasketItem) = basketRepository.updateBasketItemQuantity(basketItem)
}
package com.oguzhandongul.shoppingapp.features.basket.presentation.uistates

import com.oguzhandongul.shoppingapp.product.model.BasketItem

sealed class BasketUiState {
    object Empty : BasketUiState()
    data class Success(val basketItems: List<BasketItem>) : BasketUiState()
}
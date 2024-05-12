package com.oguzhandongul.shoppingapp.features.cart.presentation.uistates

import com.oguzhandongul.shoppingapp.product.model.CartItem

sealed class CartUiState {
    object Empty : CartUiState()
    data class Success(val cartItems: List<CartItem>) : CartUiState()
}
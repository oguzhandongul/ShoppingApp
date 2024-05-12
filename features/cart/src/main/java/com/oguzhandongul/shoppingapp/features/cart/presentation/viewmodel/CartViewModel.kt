package com.oguzhandongul.shoppingapp.features.cart.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzhandongul.shoppingapp.features.cart.domain.ClearCartUseCase
import com.oguzhandongul.shoppingapp.features.cart.domain.GetCartItemsUseCase
import com.oguzhandongul.shoppingapp.features.cart.domain.RemoveFromCartUseCase
import com.oguzhandongul.shoppingapp.features.cart.domain.UpdateCartItemQuantityUseCase
import com.oguzhandongul.shoppingapp.features.cart.presentation.uistates.CartUiState
import com.oguzhandongul.shoppingapp.product.model.CartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val updateCartItemQuantityUseCase: UpdateCartItemQuantityUseCase,
    private val clearCartUseCase: ClearCartUseCase
) : ViewModel() {

    val state: StateFlow<CartUiState> = getCartItemsUseCase()
        .map<List<CartItem>, CartUiState> { cartItems ->
            if (cartItems.isEmpty()) {
                CartUiState.Empty
            } else {
                CartUiState.Success(cartItems)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CartUiState.Empty)

    fun removeFromCart(cartItem: CartItem) {
        viewModelScope.launch {
            removeFromCartUseCase(cartItem.productId)
        }
    }

    fun updateCartItemQuantity(cartItem: CartItem, newQuantity: Int) {
        viewModelScope.launch {
            if (newQuantity > 0) {
                updateCartItemQuantityUseCase(cartItem.copy(quantity = newQuantity))
            } else {
                removeFromCartUseCase(cartItem.productId) // Remove if quantity is 0
            }
        }
    }

    fun emptyCart() {
        viewModelScope.launch {
            clearCartUseCase()
        }
    }
}
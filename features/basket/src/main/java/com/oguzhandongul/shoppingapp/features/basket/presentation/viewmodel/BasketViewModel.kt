package com.oguzhandongul.shoppingapp.features.basket.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzhandongul.shoppingapp.features.basket.domain.ClearBasketUseCase
import com.oguzhandongul.shoppingapp.features.basket.domain.GetBasketItemsUseCase
import com.oguzhandongul.shoppingapp.features.basket.domain.RemoveFromBasketUseCase
import com.oguzhandongul.shoppingapp.features.basket.domain.UpdateBasketItemQuantityUseCase
import com.oguzhandongul.shoppingapp.features.basket.presentation.uistates.BasketUiState
import com.oguzhandongul.shoppingapp.product.model.BasketItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val getBasketItemsUseCase: GetBasketItemsUseCase,
    private val removeFromBasketUseCase: RemoveFromBasketUseCase,
    private val updateBasketItemQuantityUseCase: UpdateBasketItemQuantityUseCase,
    private val clearBasketUseCase: ClearBasketUseCase
) : ViewModel() {

    val state: StateFlow<BasketUiState> = getBasketItemsUseCase()
        .map<List<BasketItem>, BasketUiState> { basketItems ->
            if (basketItems.isEmpty()) {
                BasketUiState.Empty
            } else {
                BasketUiState.Success(basketItems)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BasketUiState.Empty)

    fun removeFromBasket(basketItem: BasketItem) {
        viewModelScope.launch {
            removeFromBasketUseCase(basketItem.productId)
        }
    }

    fun updateBasketItemQuantity(basketItem: BasketItem, newQuantity: Int) {
        viewModelScope.launch {
            if (newQuantity > 0) {
                updateBasketItemQuantityUseCase(basketItem.copy(quantity = newQuantity))
            } else {
                removeFromBasketUseCase(basketItem.productId) // Remove if quantity is 0
            }
        }
    }

    fun clearBasket() {
        viewModelScope.launch {
            clearBasketUseCase()
        }
    }
}
package com.oguzhandongul.shoppingapp.productlist.presentation.uistates

import com.oguzhandongul.shoppingapp.product.model.Product

sealed interface ProductListUiState {
    object Loading : ProductListUiState
    data class Success(val data: List<Product>) : ProductListUiState
    data class Error(val message: String) : ProductListUiState
}
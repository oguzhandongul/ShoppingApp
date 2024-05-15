package com.oguzhandongul.shoppingapp.features.productdetail.presentation.uistates

import com.oguzhandongul.shoppingapp.product.model.Product

sealed class ProductDetailUiState {
    object Loading : ProductDetailUiState()
    data class Success(val product: Product) : ProductDetailUiState()
    data class Error(val error: String) : ProductDetailUiState()
}
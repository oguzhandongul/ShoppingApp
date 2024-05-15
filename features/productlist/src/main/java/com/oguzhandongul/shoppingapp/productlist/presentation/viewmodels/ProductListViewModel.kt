package com.oguzhandongul.shoppingapp.productlist.presentation.viewmodels

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzhandongul.shoppingapp.core.util.utils.Resource
import com.oguzhandongul.shoppingapp.product.model.Product
import com.oguzhandongul.shoppingapp.productlist.domain.usecase.AddToCartUseCase
import com.oguzhandongul.shoppingapp.productlist.domain.usecase.CacheProductListUseCase
import com.oguzhandongul.shoppingapp.productlist.domain.usecase.GetCartItemCountUseCase
import com.oguzhandongul.shoppingapp.productlist.domain.usecase.GetProductListUseCase
import com.oguzhandongul.shoppingapp.productlist.presentation.uistates.ProductListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase,
    private val cacheProductListUseCase: CacheProductListUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    getCartUseCase: GetCartItemCountUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductListUiState>(ProductListUiState.Loading)
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    val cartItemCount: StateFlow<Int> = getCartUseCase().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        0
    )

    init {
        cacheProducts()
    }

    @VisibleForTesting
    fun cacheProducts() {
        viewModelScope.launch {
            cacheProductListUseCase().collect { resource ->
                when (resource) {
                    is Resource.Loading -> _uiState.value = ProductListUiState.Loading
                    is Resource.Success -> loadProductsData()
                    is Resource.Error -> _uiState.value =
                        ProductListUiState.Error(resource.message!!)
                }
            }
        }
    }

    fun loadProductsData() {
        viewModelScope.launch {
            getProductListUseCase().collect { resource ->
                _uiState.value = when (resource) {
                    is Resource.Loading -> ProductListUiState.Loading
                    is Resource.Success -> ProductListUiState.Success(resource.data ?: emptyList())
                    is Resource.Error -> ProductListUiState.Error(
                        resource.message ?: "Unknown error"
                    )
                }
            }
        }
    }


    fun addToCart(product: Product) {
        viewModelScope.launch {
            addToCartUseCase(product).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        // Potentially show a success message here or update UI accordingly
                    }

                    is Resource.Error -> {
                        // Show error message to the user
                    }

                    is Resource.Loading -> {
                        // Show a loading indicator if this takes a while
                    }
                }
            }
        }
    }
}
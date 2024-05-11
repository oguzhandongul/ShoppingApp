package com.oguzhandongul.shoppingapp.productlist.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzhandongul.shoppingapp.core.util.utils.Resource
import com.oguzhandongul.shoppingapp.productlist.domain.usecase.CacheProductListUseCase
import com.oguzhandongul.shoppingapp.productlist.domain.usecase.GetProductListUseCase
import com.oguzhandongul.shoppingapp.productlist.presentation.uistates.ProductListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase,
    private val cacheProductListUseCase: CacheProductListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductListUiState>(ProductListUiState.Loading)
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    init {
        cacheProducts()
    }

    private fun cacheProducts() {
        viewModelScope.launch {
            cacheProductListUseCase().collect { resource ->
                when (resource) {
                    is Resource.Loading -> _uiState.value = ProductListUiState.Loading
                    is Resource.Success -> loadProductsData()
                    is Resource.Error -> _uiState.value = ProductListUiState.Error(resource.message!!)
                }
            }
        }
    }

    private fun loadProductsData() {
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

    fun addToBasket(id: String, isAdded: Boolean) {
        viewModelScope.launch {
            //TODO add to basket
        }
    }
}
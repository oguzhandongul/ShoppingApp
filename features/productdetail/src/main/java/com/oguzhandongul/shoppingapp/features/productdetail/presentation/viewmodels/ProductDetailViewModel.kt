package com.oguzhandongul.shoppingapp.features.productdetail.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzhandongul.shoppingapp.core.util.utils.Resource
import com.oguzhandongul.shoppingapp.features.productdetail.domain.usecases.AddToCartUseCase
import com.oguzhandongul.shoppingapp.features.productdetail.domain.usecases.GetProductDetailUseCase
import com.oguzhandongul.shoppingapp.features.productdetail.presentation.uistates.ProductDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getProductDetailUseCase: GetProductDetailUseCase,
    private val addToCarttUseCase: AddToCartUseCase
) : ViewModel() {

    private val productId = savedStateHandle.get<String>("productId") ?: ""

    private val _state = MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Loading)
    val state: StateFlow<ProductDetailUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = ProductDetailUiState.Loading
            getProductDetailUseCase(productId).collect { result ->
                _state.value = when (result) {
                    is Resource.Success -> ProductDetailUiState.Success(result.data!!)
                    is Resource.Error -> ProductDetailUiState.Error(result.message ?: "Unknown error")
                    else -> _state.value // Keep the current state for other cases
                }
            }
        }
    }

    fun onAddToCartClicked() {
        viewModelScope.launch {
            state.value.let {
                if (it is ProductDetailUiState.Success) {
                    addToCarttUseCase(it.product).collect { cartResult ->
                        when (cartResult) {
                            is Resource.Success<*> -> {
                                // Product added successfully
                                // Show a message or navigate to the basket
                            }
                            is Resource.Error<*> -> {
                                // Handle the error (e.g., show a toast)
                            }
                            else -> {} // Loading state, no need to do anything here
                        }
                    }
                }
            }
        }
    }

    fun onBackClicked() {
        //TODO Navigate Back
    }
}
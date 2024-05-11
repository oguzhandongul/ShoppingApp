package com.oguzhandongul.shoppingapp.productlist.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzhandongul.shoppingapp.core.util.utils.ResourceHelper
import com.oguzhandongul.shoppingapp.productlist.R
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
    private val resourceHelper: ResourceHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductListUiState>(ProductListUiState.Loading)
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    init {
        loadProductsData()
    }

    private fun loadProductsData() {
        viewModelScope.launch {
            _uiState.value = ProductListUiState.Loading
            val result = getProductListUseCase.invoke().getOrNull()
            if (result != null) {
                _uiState.value = ProductListUiState.Success(result)
            } else {
                _uiState.value =
                    ProductListUiState.Error(resourceHelper.getString(R.string.error_loading_list))
            }
        }
    }

    fun addToBasket(id: String, isAdded: Boolean) {
        viewModelScope.launch {
            //TODO add to basket
        }
    }
}
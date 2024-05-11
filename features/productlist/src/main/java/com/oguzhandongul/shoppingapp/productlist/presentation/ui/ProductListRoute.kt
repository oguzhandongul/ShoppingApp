package com.oguzhandongul.shoppingapp.productlist.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import com.oguzhandongul.shoppingapp.core.ui.components.ErrorView
import com.oguzhandongul.shoppingapp.product.model.Product
import com.oguzhandongul.shoppingapp.productlist.presentation.uistates.ProductListUiState
import com.oguzhandongul.shoppingapp.productlist.presentation.viewmodels.ProductListViewModel


@Composable
fun ProductListRoute(
    onGoToItem: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProductListViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    ListScreen(state, onGoToItem, viewModel::addToBasket, modifier)
}

@Composable
internal fun ListScreen(
    state: ProductListUiState,
    onGoToItem: (String) -> Unit,
    onBasketItem: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    when (state) {
        ProductListUiState.Loading -> ProductListUiState.Loading
        is ProductListUiState.Success -> Content(state.data, onGoToItem, onBasketItem, modifier)
        is ProductListUiState.Error -> ErrorView(exception = Exception()) {
        }
    }
}

@Composable
internal fun Content(
    items: List<Product>,
    onGoToItem: (String) -> Unit,
    onBasketItem: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        itemsIndexed(items = items) { index, item ->
            ListItem(
                headlineContent = { Text(text = item.name) },
                trailingContent = { Text(text = item.type) },
                leadingContent = {
//                    Checkbox(
//                        checked = item.isBookmarked,
//                        onCheckedChange = { isChecked ->
//                            onBookmarkItem(item.id, isChecked)
//                        }
//                    )
                },
                modifier = Modifier
                    .clickable {
                        onGoToItem(item.id)
                    }
                    .testTag("item_$index")
            )
        }
    }
}
package com.oguzhandongul.shoppingapp.productlist.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.oguzhandongul.shoppingapp.core.ui.components.CoilImage
import com.oguzhandongul.shoppingapp.core.ui.components.ErrorView
import com.oguzhandongul.shoppingapp.core.ui.theme.Dimensions
import com.oguzhandongul.shoppingapp.product.model.Product
import com.oguzhandongul.shoppingapp.productlist.R
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
    onBasketItem: (Product, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    when (state) {
        ProductListUiState.Loading -> ProductListUiState.Loading
        is ProductListUiState.Success -> Content(state.data, onGoToItem, onBasketItem, modifier)
        is ProductListUiState.Error -> ErrorView(exception = Exception()) {
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Content(
    items: List<Product>,
    onGoToItem: (String) -> Unit,
    onBasketItem: (Product, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.title_product_list)) })
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Two columns in the grid
            contentPadding = PaddingValues(Dimensions.small), // Add some padding
            modifier = modifier.padding(paddingValues)
        ) {
            items(items.size) { index ->
                ProductCard(items[index], onBasketItem, modifier = Modifier
                    .clickable {
                        onGoToItem(items[index].id)
                    }
                    .testTag("item_$index"))
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, onBasketItem: (Product, Boolean) -> Unit, modifier: Modifier) {
    Card(
        modifier = modifier
            .padding(Dimensions.small)
            .background(color = Color.White)
            .fillMaxWidth()
            .clickable {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            CoilImage(
                url = product.imageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Column(
                modifier = Modifier.padding(Dimensions.medium) // Padding applied here
            ) {
                Text(text = product.name, fontWeight = FontWeight.Bold)
                Text(text = product.type)
                Text(
                    text = "${product.price.value} ${product.price.currency}",
                    fontWeight = FontWeight.Bold
                )

                // Add to Basket Button
                Spacer(modifier = Modifier.height(Dimensions.medium))
                Button(onClick = { onBasketItem(product, true) }) {
                    Text(stringResource(id = R.string.label_add_to_basket))
                }
            }
        }
    }
}
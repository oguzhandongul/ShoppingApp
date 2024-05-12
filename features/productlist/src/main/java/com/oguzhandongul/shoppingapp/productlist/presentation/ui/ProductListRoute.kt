package com.oguzhandongul.shoppingapp.productlist.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
    onGoToBasket: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProductListViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val basketItemCount by viewModel.basketItemCount.collectAsState(initial = 0) // Observe item count

    ListScreen(
        state = state,
        onGoToItem = onGoToItem,
        onBasketItem = viewModel::addToBasket,
        onGoToBasket = onGoToBasket, // Pass the callback
        basketItemCount = basketItemCount, // Pass the count
        modifier = modifier
    )
}

@Composable
internal fun ListScreen(
    state: ProductListUiState,
    onGoToItem: (String) -> Unit,
    onBasketItem: (Product) -> Unit,
    basketItemCount: Int,
    onGoToBasket: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {  // Use Box for positioning
            when (state) {
                ProductListUiState.Loading -> ProductListUiState.Loading // Handle loading state
                is ProductListUiState.Success -> Content(
                    state.data,
                    onGoToItem,
                    onBasketItem,
                    modifier = Modifier.fillMaxSize() // Make content fill the remaining space
                )

                is ProductListUiState.Error -> ErrorView(exception = Exception()) { } // Handle error state
            }

            BasketFloatingActionButton(
                itemCount = basketItemCount,
                onClick = onGoToBasket,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(Dimensions.medium)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Content(
    items: List<Product>,
    onGoToItem: (String) -> Unit,
    onBasketItem: (Product) -> Unit,
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
fun ProductCard(product: Product, onBasketItem: (Product) -> Unit, modifier: Modifier) {
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
                Button(onClick = { onBasketItem(product) }) {
                    Text(stringResource(id = R.string.label_add_to_basket))
                }
            }
        }
    }
}

@Composable
fun BasketFloatingActionButton(
    itemCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        icon = {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Shopping Basket"
            )
        },
        text = { Text("Basket ($itemCount)") } // Include item count in the text
    )
}
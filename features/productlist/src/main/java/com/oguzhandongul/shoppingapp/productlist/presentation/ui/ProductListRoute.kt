package com.oguzhandongul.shoppingapp.productlist.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.oguzhandongul.shoppingapp.core.ui.components.AnimatedButton
import com.oguzhandongul.shoppingapp.core.ui.components.AnimateOnValueChange
import com.oguzhandongul.shoppingapp.core.ui.components.CoilImage
import com.oguzhandongul.shoppingapp.core.ui.components.ErrorView
import com.oguzhandongul.shoppingapp.core.ui.components.ProductLabel
import com.oguzhandongul.shoppingapp.core.ui.extensions.animatedShakeAndScale
import com.oguzhandongul.shoppingapp.core.ui.theme.Dimensions
import com.oguzhandongul.shoppingapp.product.model.Product
import com.oguzhandongul.shoppingapp.productlist.R
import com.oguzhandongul.shoppingapp.productlist.presentation.uistates.ProductListUiState
import com.oguzhandongul.shoppingapp.productlist.presentation.viewmodels.ProductListViewModel


@Composable
fun ProductListRoute(
    onGoToItem: (String) -> Unit,
    onGoToCart: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProductListViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val cartItemCount by viewModel.cartItemCount.collectAsState(initial = 0) // Observe item count

    ListScreen(
        state = state,
        onGoToItem = onGoToItem,
        onCartItem = viewModel::addToCart,
        onGoToCart = onGoToCart, // Pass the callback
        cartItemCount = cartItemCount, // Pass the count
        modifier = modifier
    )
}

@Composable
internal fun ListScreen(
    state: ProductListUiState,
    onGoToItem: (String) -> Unit,
    onCartItem: (Product) -> Unit,
    cartItemCount: Int,
    onGoToCart: () -> Unit,
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
                    onCartItem,
                    modifier = Modifier.fillMaxSize() // Make content fill the remaining space
                )

                is ProductListUiState.Error -> ErrorView(exception = Exception()) { } // Handle error state
            }

            CartFloatingActionButton(
                itemCount = cartItemCount,
                onClick = onGoToCart,
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
    onCartItem: (Product) -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.title_product_list)) })
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Two columns in the grid
            contentPadding = PaddingValues(
                Dimensions.small,
                Dimensions.small,
                Dimensions.small,
                Dimensions.xxlarge
            ), // Add some padding
            modifier = modifier.padding(paddingValues)
        ) {
            items(items, key = { item -> item.id }) { product ->
                ProductCard(product, onCartItem, modifier = Modifier
                    .clickable {
                        onGoToItem(product.id)
                    }
                )
            }
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    onCartItem: (Product) -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(Dimensions.small)
                .fillMaxWidth(),
        ) {
            Box(modifier = modifier) {
                CoilImage(
                    url = product.imageUrl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
                ProductLabel(text = product.type, modifier = Modifier.align(Alignment.TopStart))
            }
            Column(
                modifier = Modifier.padding(Dimensions.medium) // Padding applied here
            ) {
                Text(
                    text = product.name,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${product.price.value} ${product.price.currency}",
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(Dimensions.medium))
                Box(modifier = Modifier.fillMaxWidth()) {
                    AnimatedButton(
                        onClick = { onCartItem(product) },
                        modifier = Modifier
                            .width(60.dp)
                            .height(40.dp)
                            .align(Alignment.BottomEnd)
                    )
                }

            }
        }
    }
}

@Composable
fun CartFloatingActionButton(
    itemCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimateOnValueChange(itemCount) { shouldAnimate ->
        ExtendedFloatingActionButton(
            onClick = onClick,
            modifier = modifier.animatedShakeAndScale(shouldAnimate = shouldAnimate),
            icon = {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Shopping Cart"
                )
            },
            text = { Text("Cart ($itemCount)") }
        )
    }
}
package com.oguzhandongul.shoppingapp.features.basket.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.oguzhandongul.shoppingapp.core.ui.components.CoilImage
import com.oguzhandongul.shoppingapp.core.ui.theme.Dimensions
import com.oguzhandongul.shoppingapp.features.basket.R
import com.oguzhandongul.shoppingapp.features.basket.presentation.uistates.BasketUiState
import com.oguzhandongul.shoppingapp.features.basket.presentation.viewmodel.BasketViewModel
import com.oguzhandongul.shoppingapp.product.model.BasketItem

@Composable
fun BasketRoute(
    viewModel: BasketViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    BasketScreen(
        state = state,
        onBackClick = onBackClick,
        onRemoveItem = viewModel::removeFromBasket,
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BasketScreen(
    state: BasketUiState,
    onBackClick: () -> Unit,
    onRemoveItem: (BasketItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.title_cart)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        when (state) {
            is BasketUiState.Empty -> BasketEmptyContent(modifier = Modifier.padding(paddingValues))
            is BasketUiState.Success -> BasketContent(
                basketItems = state.basketItems,
                onRemoveItem = onRemoveItem,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
fun BasketEmptyContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(Dimensions.medium))
        Text(text = stringResource(id = R.string.error_empty_cart))
    }
}

@Composable
fun BasketContent(
    basketItems: List<BasketItem>,
    onRemoveItem: (BasketItem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(basketItems) { basketItem ->
            BasketItemCard(basketItem, onRemoveItem)
        }
    }
}

@Composable
fun BasketItemCard(basketItem: BasketItem, onRemoveItem: (BasketItem) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoilImage(
                url = basketItem.product.imageUrl,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(Dimensions.medium))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = basketItem.product.name, fontWeight = FontWeight.Bold)
                Text(text = "${basketItem.quantity} x ${basketItem.product.price.value} ${basketItem.product.price.currency}")
            }
            IconButton(onClick = {
                onRemoveItem(
                    BasketItem(
                        ids = basketItem.product.id.toInt(),
                        productId = basketItem.product.id,
                        product = basketItem.product,
                        quantity = basketItem.quantity
                    )
                )
            }) { // You might need to change the implementation of this function according to your remove function.
                Icon(Icons.Default.Delete, contentDescription = "Remove from basket")
            }
        }
    }
}
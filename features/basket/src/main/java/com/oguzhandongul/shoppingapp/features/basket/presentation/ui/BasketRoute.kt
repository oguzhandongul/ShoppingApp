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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
        onUpdateQuantity = viewModel::updateBasketItemQuantity,
        onClearBasket = viewModel::clearBasket,
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BasketScreen(
    state: BasketUiState,
    onBackClick: () -> Unit,
    onRemoveItem: (BasketItem) -> Unit,
    onUpdateQuantity: (BasketItem, Int) -> Unit,
    onClearBasket: () -> Unit,
    modifier: Modifier = Modifier
) {

    var totalPrice by remember { mutableStateOf(0.0) }
    var totalItemCount by remember { mutableStateOf(0) }
    var showClearBasketConfirmation by remember { mutableStateOf(false) }


    LaunchedEffect(state) {
        when (state) {
            is BasketUiState.Success -> {
                totalPrice = state.basketItems.sumOf { it.product.price.value * it.quantity }
                totalItemCount = state.basketItems.sumOf { it.quantity }
            }

            BasketUiState.Empty -> {
                totalPrice = 0.0
                totalItemCount = 0
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.title_cart)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (state is BasketUiState.Success && state.basketItems.isNotEmpty()) {
                        IconButton(onClick = { showClearBasketConfirmation = true }) {
                            Icon(Icons.Default.Delete, contentDescription = "Clear Basket")
                        }
                    }
                }

            )
        },
        bottomBar = {
            BasketBottomBar(totalPrice, totalItemCount)
        }
    ) { paddingValues ->
        when (state) {
            is BasketUiState.Empty -> BasketEmptyContent(modifier = Modifier.padding(paddingValues))
            is BasketUiState.Success -> BasketContent(
                basketItems = state.basketItems,
                onRemoveItem = onRemoveItem,
                onUpdateQuantity = onUpdateQuantity,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }

    ClearBasketConfirmationDialog(
        showDialog = showClearBasketConfirmation,
        onConfirm = { onClearBasket() },
        onDismiss = { showClearBasketConfirmation = false }
    )
}

@Composable
fun ClearBasketConfirmationDialog(showDialog: Boolean, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss, // Called when the dialog is dismissed (e.g., by clicking outside or pressing the back button)
            title = { Text(stringResource(id = R.string.title_empty_cart)) },
            text = { Text(stringResource(id = R.string.desc_empty_cart)) },
            confirmButton = {
                Button(onClick = {
                    onConfirm() // Call the onConfirm function first to clear the basket
                    onDismiss() // Then dismiss the dialog
                }) {
                    Text(stringResource(id = com.oguzhandongul.shoppingapp.core.ui.R.string.label_yes))
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text(stringResource(id = com.oguzhandongul.shoppingapp.core.ui.R.string.label_no))
                }
            }
        )
    }
}

@Composable
private fun BasketBottomBar(totalPrice: Double, totalItemCount: Int) {
    Surface(
        modifier = Modifier.shadow(elevation = Dimensions.medium),
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.medium),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End
        ) {
            Text("Total Price: $totalPrice kr")
            Text("Total Items: $totalItemCount")
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
    onUpdateQuantity: (BasketItem, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(basketItems) { basketItem ->
            BasketItemCard(basketItem, onRemoveItem, onUpdateQuantity)
        }
    }
}

@Composable
fun BasketItemCard(
    basketItem: BasketItem,
    onRemoveItem: (BasketItem) -> Unit,
    onUpdateQuantity: (BasketItem, Int) -> Unit
) {

    var showQuantityDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(Dimensions.small)
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
                Text(text = "${basketItem.product.price.value} ${basketItem.product.price.currency}")
                // Quantity Button
                Button(
                    onClick = { showQuantityDialog = true },
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Text("${basketItem.quantity}")
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Update quantity")
                }
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
            }) {
                Icon(Icons.Default.Delete, contentDescription = "Remove from basket")
            }

        }
    }
    // Quantity Selection Dialog
    if (showQuantityDialog) {
        QuantityDialog(
            initialQuantity = basketItem.quantity,
            onDismissRequest = { showQuantityDialog = false },
            onQuantitySelected = { newQuantity ->
                onUpdateQuantity(basketItem, newQuantity)
                showQuantityDialog = false
            }
        )
    }
}

@Composable
fun QuantityDialog(
    initialQuantity: Int,
    onDismissRequest: () -> Unit,
    onQuantitySelected: (Int) -> Unit
) {
    var selectedQuantity by remember { mutableStateOf(initialQuantity) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(id = R.string.title_select_quantity)) },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) { // Scrollable column
                for (quantity in 0..10) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = (quantity == selectedQuantity),
                            onClick = { selectedQuantity = quantity }
                        )
                        Text(text = "$quantity")
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = { onQuantitySelected(selectedQuantity) }) {
                Text(stringResource(id = com.oguzhandongul.shoppingapp.core.ui.R.string.label_ok))
            }
        }
    )
}
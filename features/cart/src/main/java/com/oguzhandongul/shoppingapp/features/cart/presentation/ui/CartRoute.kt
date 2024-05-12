package com.oguzhandongul.shoppingapp.features.cart.presentation.ui

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
import com.oguzhandongul.shoppingapp.features.cart.R
import com.oguzhandongul.shoppingapp.features.cart.presentation.uistates.CartUiState
import com.oguzhandongul.shoppingapp.features.cart.presentation.viewmodel.CartViewModel
import com.oguzhandongul.shoppingapp.product.model.CartItem

@Composable
fun CartRoute(
    viewModel: CartViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    CartScreen(
        state = state,
        onBackClick = onBackClick,
        onRemoveItem = viewModel::removeFromCart,
        onUpdateQuantity = viewModel::updateCartItemQuantity,
        onEmptyCart = viewModel::emptyCart,
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CartScreen(
    state: CartUiState,
    onBackClick: () -> Unit,
    onRemoveItem: (CartItem) -> Unit,
    onUpdateQuantity: (CartItem, Int) -> Unit,
    onEmptyCart: () -> Unit,
    modifier: Modifier = Modifier
) {

    var totalPrice by remember { mutableStateOf(0.0) }
    var totalItemCount by remember { mutableStateOf(0) }
    var showEmptyCartConfirmation by remember { mutableStateOf(false) }


    LaunchedEffect(state) {
        when (state) {
            is CartUiState.Success -> {
                totalPrice = state.cartItems.sumOf { it.product.price.value * it.quantity }
                totalItemCount = state.cartItems.sumOf { it.quantity }
            }

            CartUiState.Empty -> {
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
                    if (state is CartUiState.Success && state.cartItems.isNotEmpty()) {
                        IconButton(onClick = { showEmptyCartConfirmation = true }) {
                            Icon(Icons.Default.Delete, contentDescription = "Empty Cart")
                        }
                    }
                }

            )
        },
        bottomBar = {
            CartBottomBar(totalPrice, totalItemCount)
        }
    ) { paddingValues ->
        when (state) {
            is CartUiState.Empty -> CartEmptyContent(modifier = Modifier.padding(paddingValues))
            is CartUiState.Success -> CartContent(
                cartItems = state.cartItems,
                onRemoveItem = onRemoveItem,
                onUpdateQuantity = onUpdateQuantity,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }

    EmptyCartConfirmationDialog(
        showDialog = showEmptyCartConfirmation,
        onConfirm = { onEmptyCart() },
        onDismiss = { showEmptyCartConfirmation = false }
    )
}

@Composable
fun EmptyCartConfirmationDialog(showDialog: Boolean, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss, // Called when the dialog is dismissed (e.g., by clicking outside or pressing the back button)
            title = { Text(stringResource(id = R.string.title_empty_cart)) },
            text = { Text(stringResource(id = R.string.desc_empty_cart)) },
            confirmButton = {
                Button(onClick = {
                    onConfirm() // Call the onConfirm function first to empty the cart
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
private fun CartBottomBar(totalPrice: Double, totalItemCount: Int) {
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
fun CartEmptyContent(
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
fun CartContent(
    cartItems: List<CartItem>,
    onRemoveItem: (CartItem) -> Unit,
    onUpdateQuantity: (CartItem, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(cartItems) { cartItem ->
            CartItemCard(cartItem, onRemoveItem, onUpdateQuantity)
        }
    }
}

@Composable
fun CartItemCard(
    cartItem: CartItem,
    onRemoveItem: (CartItem) -> Unit,
    onUpdateQuantity: (CartItem, Int) -> Unit
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
                url = cartItem.product.imageUrl,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(Dimensions.medium))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = cartItem.product.name, fontWeight = FontWeight.Bold)
                Text(text = "${cartItem.product.price.value} ${cartItem.product.price.currency}")
                // Quantity Button
                Button(
                    onClick = { showQuantityDialog = true },
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Text("${cartItem.quantity}")
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Update quantity")
                }
            }
            IconButton(onClick = {
                onRemoveItem(
                    CartItem(
                        ids = cartItem.product.id.toInt(),
                        productId = cartItem.product.id,
                        product = cartItem.product,
                        quantity = cartItem.quantity
                    )
                )
            }) {
                Icon(Icons.Default.Delete, contentDescription = "Remove from cart")
            }

        }
    }
    // Quantity Selection Dialog
    if (showQuantityDialog) {
        QuantityDialog(
            initialQuantity = cartItem.quantity,
            onDismissRequest = { showQuantityDialog = false },
            onQuantitySelected = { newQuantity ->
                onUpdateQuantity(cartItem, newQuantity)
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
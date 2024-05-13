package com.oguzhandongul.shoppingapp.features.productdetail.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.oguzhandongul.shoppingapp.core.ui.components.AnimatedButton
import com.oguzhandongul.shoppingapp.core.ui.components.CoilImage
import com.oguzhandongul.shoppingapp.core.ui.components.ErrorView
import com.oguzhandongul.shoppingapp.core.ui.components.LoadingView
import com.oguzhandongul.shoppingapp.core.ui.theme.Dimensions
import com.oguzhandongul.shoppingapp.features.productdetail.R
import com.oguzhandongul.shoppingapp.features.productdetail.presentation.uistates.ProductDetailUiState
import com.oguzhandongul.shoppingapp.features.productdetail.presentation.viewmodels.ProductDetailViewModel
import com.oguzhandongul.shoppingapp.product.model.Product

@Composable
fun ProductDetailRoute(
    productId: String?,
    onBackClick: () -> Unit,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    ProductDetailScreen(
        state = state,
        onAddToCartClick = { viewModel.onAddToCartClicked() },
        onBackClick = onBackClick,
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProductDetailScreen(
    state: ProductDetailUiState,
    onAddToCartClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.title_product_detail)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        when (state) {
            is ProductDetailUiState.Loading -> LoadingView()
            is ProductDetailUiState.Error -> ErrorView(exception = Exception(state.error)) { onBackClick() }
            is ProductDetailUiState.Success -> {
                ProductDetailContent(
                    product = state.product,
                    onAddToCartClick = onAddToCartClick,
                    modifier = Modifier.padding(paddingValues))
            }
        }
    }
}

@Composable
fun ProductDetailContent(
    product: Product,
    onAddToCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        CoilImage(
            url = product.imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
        )
        Spacer(modifier = Modifier.height(Dimensions.medium))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.medium)
        ) {
            Text(
                text = product.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(Dimensions.small))
            Text(text = product.type)
            Spacer(modifier = Modifier.height(Dimensions.small))
            Text(text = "${product.price.value} ${product.price.currency}", fontSize = 18.sp)

            Spacer(modifier = Modifier.height(Dimensions.medium))

            product.info.material?.let { material ->
                Text(text = "Material: $material")
                Spacer(modifier = Modifier.height(4.dp))
            }

            product.info.color.let { color ->
                Text(text = "Color: $color")
                Spacer(modifier = Modifier.height(4.dp))
            }

            product.info.numberOfSeats?.let { numberOfSeats ->
                Text(text = "Number of Seats: $numberOfSeats")
                Spacer(modifier = Modifier.height(4.dp))
            }

            Spacer(modifier = Modifier.height(Dimensions.medium))
            AnimatedButton(
                text = "Add to Cart",
                onClick = onAddToCartClick,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}


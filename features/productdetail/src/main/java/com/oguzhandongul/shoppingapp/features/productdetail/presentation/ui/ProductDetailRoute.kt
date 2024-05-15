package com.oguzhandongul.shoppingapp.features.productdetail.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.oguzhandongul.shoppingapp.core.ui.components.AnimatedButton
import com.oguzhandongul.shoppingapp.core.ui.components.CoilImage
import com.oguzhandongul.shoppingapp.core.ui.components.ErrorView
import com.oguzhandongul.shoppingapp.core.ui.components.LoadingView
import com.oguzhandongul.shoppingapp.core.ui.components.ProductLabel
import com.oguzhandongul.shoppingapp.core.ui.theme.Dimensions
import com.oguzhandongul.shoppingapp.core.util.extensions.toCurrencyString
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
                    modifier = Modifier.padding(paddingValues)
                )
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
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = modifier.verticalScroll(rememberScrollState())) {
            CoilImage(
                url = product.imageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f / 1f)
            )
            Spacer(modifier = Modifier.height(Dimensions.medium))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimensions.medium)
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = product.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    ProductLabel(text = product.type)
                }
                Spacer(modifier = Modifier.height(Dimensions.small))

                Spacer(modifier = Modifier.height(Dimensions.medium))
                DetailItem(title = "Material", product.info.material)
                DetailItem(title = "Color", product.info.color)
                DetailItem(title = "Number of Seats", product.info.numberOfSeats?.toString())
                Spacer(modifier = Modifier.height(100.dp))


            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .shadow(8.dp)
                .background(Color.White)
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomEnd)
                .padding(Dimensions.medium, Dimensions.medium, Dimensions.medium, Dimensions.small)

        ) {
            PriceItem(
                currency = product.price.currency,
                value = product.price.value.toCurrencyString(),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            AnimatedButton(
                onClick = onAddToCartClick,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .width(180.dp)
                    .height(56.dp)
            )
        }
    }

}

@Composable
fun DetailItem(title: String, value: String?) {
    value?.let {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "$title:", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.width(Dimensions.small))
            Text(text = it)
        }
        Spacer(modifier = Modifier.height(Dimensions.small))
    }
}

@Composable
fun PriceItem(currency: String, value: String?, modifier: Modifier) {
    value?.let {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = it, fontWeight = FontWeight.Bold, fontSize = 30.sp)
            Text(text = " $currency", fontWeight = FontWeight.Thin)
        }
    }
}


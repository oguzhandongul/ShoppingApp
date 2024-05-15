package com.oguzhandongul.shoppingapp.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.oguzhandongul.shoppingapp.core.ui.theme.Green700
import kotlinx.coroutines.delay

@Composable
fun AnimatedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    var showCheckmark by remember { mutableStateOf(false) }

    val backgroundColor by animateColorAsState(
        targetValue = if (showCheckmark) Green700 else MaterialTheme.colorScheme.primary,
        animationSpec = tween(durationMillis = 200)
    )

    val shoppingCartOffset by animateDpAsState(
        targetValue = if (showCheckmark) 50.dp else 0.dp, // Slide down if showCheckmark is true
        animationSpec = tween(durationMillis = 700)
    )

    val checkmarkOffset by animateDpAsState(
        targetValue = if (showCheckmark) 0.dp else (-50).dp, // Slide in from top if showCheckmark is true
        animationSpec = tween(durationMillis = 700)
    )

    LaunchedEffect(key1 = showCheckmark) {
        if (showCheckmark) {
            delay(700)
            showCheckmark = false
        }
    }

    Button(
        onClick = {
            onClick()
            showCheckmark = true
        },
        modifier = modifier,
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        )
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .align(Alignment.CenterVertically)) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Add to cart",
                tint = Color.White,
                modifier = Modifier
                    .offset(y = shoppingCartOffset)
                    .align(Alignment.Center)
            )
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Added to cart",
                tint = Color.White,
                modifier = Modifier
                    .offset(y = checkmarkOffset)
                    .align(Alignment.Center)
            )
        }
    }
}


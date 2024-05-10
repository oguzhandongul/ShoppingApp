package com.oguzhandongul.shoppingapp.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.oguzhandongul.shoppingapp.core.ui.theme.Dimensions

@Composable
fun LoadingView() {
    Box(
        contentAlignment = Alignment.Center, // Center content within the Box
        modifier = Modifier.fillMaxSize() // Make the Box fill the available space
    ) {
        CircularProgressIndicator(
            color = Color.Blue,
            strokeWidth = 2.dp,
            modifier = Modifier.size(Dimensions.xlarge)
        )
    }
}
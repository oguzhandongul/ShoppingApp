package com.oguzhandongul.shoppingapp.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.oguzhandongul.shoppingapp.core.ui.theme.Dimensions

@Composable
fun ProductLabel(text: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier.padding(Dimensions.xsmall))
    {
        Text(
            text = text,
            modifier = modifier
                .background(
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(Dimensions.small)
                )
                .padding(Dimensions.xsmall),
            color = MaterialTheme.colorScheme.onTertiary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp
        )
    }
}
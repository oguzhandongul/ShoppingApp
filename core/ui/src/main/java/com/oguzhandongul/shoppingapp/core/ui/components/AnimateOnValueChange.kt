package com.oguzhandongul.shoppingapp.core.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

@Composable
fun AnimateOnValueChange(value: Int, excludedValue: Int = 0, content: @Composable (Boolean) -> Unit) {
    var shouldAnimate by remember { mutableStateOf(false) }
    var previousValue by remember { mutableIntStateOf(value) }

    LaunchedEffect(key1 = value) { // Use itemCount as the key
        if (value != previousValue || value != excludedValue) {
            shouldAnimate = true
            delay(600)
            shouldAnimate = false
        }
        previousValue = value
    }

    content(shouldAnimate)
}
package com.oguzhandongul.shoppingapp.core.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.oguzhandongul.shoppingapp.core.ui.theme.Green700
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun AnimatedButton(
    text: String = "Add to Basket",
    icon: ImageVector = Icons.Default.Check, // Success checkmark icon
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    initiallyEnabled: Boolean = true, // Initial enabled state
    animationDelay: Long = 1000, // Delay in milliseconds for showing the icon
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(
        disabledContainerColor = Green700,  // Color when the button is disabled
        disabledContentColor = Color.White  // Text/icon color when the button is disabled
    ) // Default Material button colors
) {
    val coroutineScope = rememberCoroutineScope() // Get the coroutine scope
    var showIcon by remember { mutableStateOf(false) } // State to control icon visibility

    // Control the enabled state of the button based on `initiallyEnabled` and `showIcon`
    val buttonEnabled by remember {
        derivedStateOf {
            initiallyEnabled && !showIcon
        }
    }

    LaunchedEffect(showIcon) {
        if (showIcon) {
            coroutineScope.launch {
                delay(animationDelay)
                showIcon = false
            }
        }
    }

    Button(
        onClick = {
            showIcon = true
            onClick()
        },
        modifier = modifier,
        enabled = buttonEnabled, // Use the derived enabled state
        colors = buttonColors
    ) {
        if (showIcon) {
            // Show icon when clicked
            Icon(
                imageVector = icon,
                contentDescription = "Success"
            )
        } else {
            // Show text otherwise
            Text(text = text)
        }
    }
}
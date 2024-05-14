package com.oguzhandongul.shoppingapp.core.ui.components

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.oguzhandongul.shoppingapp.core.ui.extensions.bounceClick
import com.oguzhandongul.shoppingapp.core.ui.theme.Green700
import kotlinx.coroutines.delay

@Composable
fun AnimatedButton(
    text: String = "Add to Basket",
    icon: ImageVector = Icons.Default.Check, // Success checkmark icon
    onClick: () -> Unit,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(
        disabledContainerColor = Green700,
        disabledContentColor = Color.White
    ),
    modifier: Modifier = Modifier,
    enabled: Boolean = true // Make the button enabled/disabled based on a state
) {

    var showIcon by remember { mutableStateOf(false) } // State to control icon visibility

    // Control the enabled state of the button based on `initiallyEnabled` and `showIcon`
    val buttonEnabled by remember {
        derivedStateOf {
            enabled && !showIcon
        }
    }

    LaunchedEffect(key1 = showIcon) {
        if (showIcon) {
            delay(1000)
            showIcon = false
        }
    }

    Button(
        onClick = {
            if (enabled) {
                showIcon = true
                onClick()
            }
        },
        modifier = modifier.bounceClick(),
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
            Text(text = text, fontWeight = FontWeight.SemiBold)
        }
    }
}


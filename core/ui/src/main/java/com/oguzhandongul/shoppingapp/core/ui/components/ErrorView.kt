package com.oguzhandongul.shoppingapp.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.oguzhandongul.shoppingapp.core.ui.theme.Dimensions

@Composable
fun ErrorView(exception: Throwable, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimensions.xlarge),
        verticalArrangement = Arrangement.Center,
    ) {

        Text(
            text = "Error: ${exception.localizedMessage}",
            color = Color.Red,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.align(CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(Dimensions.medium))
        Button(onClick = { onClick.invoke() }, modifier.align(CenterHorizontally)) {
            Text(text = "Retry")
        }
    }

}

@Preview
@Composable
fun SampleErrorView() {

    ErrorView(exception = Throwable("Error Message"), onClick = {})
}
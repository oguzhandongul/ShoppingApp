package com.oguzhandongul.shoppingapp.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.oguzhandongul.shoppingapp.core.ui.R

private const val ANIM_DURATION_CROSS_FADE = 1000

@Composable
fun CoilImage(url: String, modifier: Modifier = Modifier) {
    val request = ImageRequest.Builder(LocalContext.current)
        .data(url)
        .crossfade(ANIM_DURATION_CROSS_FADE)
        .placeholder(R.drawable.placeholder_image_24)
        .error(R.drawable.placeholder_image_24)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .memoryCacheKey(url)
        .build()

    val painter = rememberAsyncImagePainter(request)

    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier
    )
}
package com.oguzhandongul.shoppingapp.product.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductInfo(
    val material: String? = null,
    val color: String,
    val numberOfSeats: Int? = null
)
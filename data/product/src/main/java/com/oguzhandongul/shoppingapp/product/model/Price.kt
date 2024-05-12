package com.oguzhandongul.shoppingapp.product.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Price(
    val value: Double,
    val currency: String
)
package com.oguzhandongul.shoppingapp.product.model

import com.squareup.moshi.JsonClass

/**
    Implementation :
    val products: ProductResponse? = jsonFileParser.parseRawFileToObject(R.raw.products)
**/
@JsonClass(generateAdapter = true)
data class Product(
    val id: String,
    val name: String,
    val price: Price,
    val info: ProductInfo,
    val type: String,
    val imageUrl: String
)



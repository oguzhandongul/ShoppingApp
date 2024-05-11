package com.oguzhandongul.shoppingapp.product.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

/**
    Implementation :
    val products: ProductResponse? = jsonFileParser.parseRawFileToObject(R.raw.products)
**/
@Entity(tableName = "products")
@JsonClass(generateAdapter = true)
data class Product(
    @PrimaryKey val id: String,
    val name: String,
    @Embedded val price: Price,
    @Embedded val info: ProductInfo,
    val type: String,
    val imageUrl: String
)



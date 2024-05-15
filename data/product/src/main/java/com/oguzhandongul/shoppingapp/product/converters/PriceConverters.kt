package com.oguzhandongul.shoppingapp.product.converters

import androidx.room.TypeConverter
import com.oguzhandongul.shoppingapp.product.model.Price
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class PriceConverters {

    private val moshi = Moshi.Builder().build() // Create Moshi instance

    private val priceAdapter = moshi.adapter<Price>(
        Types.newParameterizedType(Price::class.java)
    ) // Create a Moshi adapter for Price

    @TypeConverter
    fun fromPrice(price: Price): String {
        return priceAdapter.toJson(price) // Convert Price to JSON string
    }

    @TypeConverter
    fun toPrice(priceString: String): Price? {
        return priceAdapter.fromJson(priceString) // Convert JSON string back to Price
    }
}
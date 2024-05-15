package com.oguzhandongul.shoppingapp.product.converters

import androidx.room.TypeConverter
import com.oguzhandongul.shoppingapp.product.enums.ProductType

class ProductTypeConverters {
    @TypeConverter
    fun fromProductType(type: ProductType): String {
        return type.name // Store as the enum name (e.g., "CHAIR", "COUCH")
    }

    @TypeConverter
    fun toProductType(typeString: String): ProductType {
        return try {
            ProductType.valueOf(typeString)
        } catch (e: IllegalArgumentException) {
            ProductType.UNKNOWN // Handle unknown types gracefully
        }
    }
}
package com.oguzhandongul.shoppingapp.product.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "basket_items")
data class BasketItem(
    @PrimaryKey(autoGenerate = true) val ids: Int = 0,
    @ColumnInfo(index = true) val productId: String,
    var quantity: Int = 1,
    @Embedded val product: Product
)
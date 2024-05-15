package com.oguzhandongul.shoppingapp.product.extensions

import com.oguzhandongul.shoppingapp.product.model.ProductInfo

fun ProductInfo.flatten(): String {
    var infoString = color
    material?.let {
        infoString += ", $it"
    }
    numberOfSeats?.let {
        infoString += ", $it seats"
    }

    return infoString
}
package com.oguzhandongul.shoppingapp.product.enums

enum class ProductType(val label: String) {
    CHAIR("chair"),
    COUCH("couch"),
    UNKNOWN("unknown")  // Handle unexpected values
}
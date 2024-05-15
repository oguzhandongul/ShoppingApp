package com.oguzhandongul.shoppingapp.core.util.utils

sealed class Screen(val route: String) {
    object ProductList : Screen("productList")
    object ProductDetail : Screen("productDetail/{productId}") {
        fun createRoute(productId: String) = "productDetail/$productId"
    }
    object Cart : Screen("cart")
}
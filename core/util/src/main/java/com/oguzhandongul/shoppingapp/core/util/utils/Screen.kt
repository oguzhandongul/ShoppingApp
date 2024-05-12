package com.oguzhandongul.shoppingapp.core.util.utils

sealed class Screen(val route: String) {
    object ProductList : Screen("ProductList")
    object ProductDetail : Screen("ProductDetail")
    object Cart : Screen("Cart")
}
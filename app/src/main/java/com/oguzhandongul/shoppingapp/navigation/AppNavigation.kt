package com.oguzhandongul.shoppingapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oguzhandongul.shoppingapp.core.util.utils.Screen
import com.oguzhandongul.shoppingapp.features.basket.presentation.ui.BasketRoute
import com.oguzhandongul.shoppingapp.productlist.presentation.ui.ProductListRoute


@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Screen.ProductList.route) {
        composable(Screen.ProductList.route) {
            ProductListRoute(
                onGoToItem = { id ->
                    navController.navigate("details/$id")
                },
                onGoToBasket = {
                    navController.navigate(Screen.Basket.route)
                }
            )
        }
//        composable(Screen.ProductDetail.route) { //TODO }
        composable(Screen.Basket.route) {
            BasketRoute(onBackClick = {})

        }
    }
}

package com.oguzhandongul.shoppingapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.oguzhandongul.shoppingapp.core.util.utils.Screen
import com.oguzhandongul.shoppingapp.features.cart.presentation.ui.CartRoute
import com.oguzhandongul.shoppingapp.features.productdetail.presentation.ui.ProductDetailRoute
import com.oguzhandongul.shoppingapp.productlist.presentation.ui.ProductListRoute


@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Screen.ProductList.route) {
        composable(Screen.ProductList.route) {
            ProductListRoute(
                onGoToItem = { id ->
                    navController.navigate("productDetail/$id")
                },
                onGoToCart = {
                    navController.navigate(Screen.Cart.route)
                }
            )
        }
        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            ProductDetailRoute(productId)
        }
        composable(Screen.Cart.route) {
            CartRoute(onBackClick = { navController.popBackStack() })

        }
    }
}

package com.example.dexlivery.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dexlivery.data.model.Product
import com.example.dexlivery.screens.MainScreen

@Composable
fun NavigationGraph(
    modifier: Modifier,
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = DexLiveryScreens.MainScreen.route) {
        composable(route = DexLiveryScreens.MainScreen.route) {
            MainScreen(modifier = modifier, onNavigateToCheckout = { selectedProductList ->
                //TODO NAVIGATE TO CHECKOUT
            })
        }
        composable(route = DexLiveryScreens.CheckoutScreen.route) {

        }
        composable(route = DexLiveryScreens.SuccessCheckoutScreen.route) {

        }
    }

}


sealed class DexLiveryScreens(val route: String) {
    object MainScreen: DexLiveryScreens("main_screen")
    object CheckoutScreen: DexLiveryScreens("checkout_screen") {
        fun createCheckout(productList: List<Product>) = "checkout_screen/$productList"
    }
    object SuccessCheckoutScreen: DexLiveryScreens("success_checkout_screen")

}
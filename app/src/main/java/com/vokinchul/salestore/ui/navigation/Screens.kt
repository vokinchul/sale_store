package com.vokinchul.salestore.ui.navigation

sealed class Screens(val name: String) {
    object MainScreen : Screens("main_screen")
    object AuthorizationScreen : Screens("authorization_screen")
    object ShoppingCartScreen : Screens("shopping_cart_screen")
    object ProductDetailScreen : Screens("product_detail_screen")
}
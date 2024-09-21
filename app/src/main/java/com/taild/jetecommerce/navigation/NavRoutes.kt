package com.taild.jetecommerce.navigation

sealed class AppRoute(val name: String = "Home") {
    object Home: AppRoute(name = "Home")
    object Cart: AppRoute(name = "Cart")
    object Profile: AppRoute(name = "Profile")
}
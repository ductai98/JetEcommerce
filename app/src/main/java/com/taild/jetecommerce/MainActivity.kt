package com.taild.jetecommerce

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.taild.domain.model.Product
import com.taild.jetecommerce.model.UiProduct
import com.taild.jetecommerce.navigation.CartRoute
import com.taild.jetecommerce.navigation.DetailsRoute
import com.taild.jetecommerce.navigation.HomeRoute
import com.taild.jetecommerce.navigation.ProfileRoute
import com.taild.jetecommerce.navigation.productNavType
import com.taild.jetecommerce.ui.feature.home.HomeScreen
import com.taild.jetecommerce.ui.theme.JetEcommerceTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.typeOf

const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetEcommerceTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                        BottomNavigationBar(
                            currentRoute = currentRoute ?: HomeRoute.toString(),
                            navigateToItem = {
                                navController.navigate(it) {
                                    popUpTo(HomeRoute) { saveState = true }
                                    launchSingleTop = true // Ensures only one instance of home is created
                                    restoreState = true
                                }
                            }
                        )
                    }
                ) {insetPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(insetPadding)
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = HomeRoute
                        ) {
                            composable<HomeRoute>(
                                enterTransition = {
                                    fadeIn(animationSpec = tween(durationMillis = 150))
                                },
                                exitTransition = {
                                    fadeOut(animationSpec = tween(durationMillis = 150))
                                }
                            ) {
                                HomeScreen(
                                    onClickProductItem = { productItem ->
                                        val product = UiProduct.fromProduct(product = productItem)
                                        navController.navigate(DetailsRoute(product))
                                    }
                                )
                            }

                            composable<CartRoute>(
                                enterTransition = {
                                    fadeIn(animationSpec = tween(durationMillis = 150))
                                },
                                exitTransition = {
                                    fadeOut(animationSpec = tween(durationMillis = 150))
                                }
                            ) {
                                Text(text = "Cart")
                            }

                            composable<ProfileRoute>(
                                enterTransition = {
                                    fadeIn(animationSpec = tween(durationMillis = 150))
                                },
                                exitTransition = {
                                    fadeOut(animationSpec = tween(durationMillis = 150))
                                }
                            ) {
                                Text(text = "Profile")
                            }

                            composable<DetailsRoute> (
                                typeMap = mapOf(typeOf<UiProduct>() to productNavType)
                            ) {
                                val route = it.toRoute<DetailsRoute>()
                                Box(modifier = Modifier.fillMaxSize()) {
                                    Text(text = route.product.title)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    currentRoute: String,
    navigateToItem: (Any) -> Unit
) {
    NavigationBar(
        modifier = modifier
    ) {
        val items = listOf(
            BottomNavItems.Home,
            BottomNavItems.Cart,
            BottomNavItems.Profile
        )

        items.forEach { item ->
            val selected = (currentRoute).substringBefore("@") == item.route::class.qualifiedName
            NavigationBarItem(
                selected = selected,
                onClick = {navigateToItem(item.route)},
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null)
                },
                label = {
                    Text(text = item.title)
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    selectedIndicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    disabledIconColor = Color.Gray,
                    disabledTextColor = Color.Gray,
                )
            )
        }
    }
}

sealed class BottomNavItems(
    val route: Any,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItems(route = HomeRoute, "Home", icon = Icons.Filled.Home)
    object Cart : BottomNavItems(route = CartRoute, "Cart", icon = Icons.Filled.ShoppingCart)
    object Profile : BottomNavItems(route = ProfileRoute, "Profile", icon = Icons.Filled.Person)
}
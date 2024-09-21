package com.taild.jetecommerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.taild.jetecommerce.navigation.AppRoute
import com.taild.jetecommerce.ui.feature.home.HomeScreen
import com.taild.jetecommerce.ui.theme.JetEcommerceTheme
import dagger.hilt.android.AndroidEntryPoint

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
                            currentRoute = currentRoute ?: AppRoute.Home.name,
                            navigateToItem = {
                                navController.navigate(it.name) {
                                    popUpTo(AppRoute.Home.name) { saveState = true }
                                    launchSingleTop = true // Ensures only one instance of home is created
                                    restoreState = true
                                }
                            }
                        )
                    }
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = AppRoute.Home.name
                        ) {
                            composable(
                                route = AppRoute.Home.name,
                                enterTransition = {
                                    fadeIn(animationSpec = tween(durationMillis = 150))
                                },
                                exitTransition = {
                                    fadeOut(animationSpec = tween(durationMillis = 150))
                                }
                            ) {
                                HomeScreen(navController)
                            }
                            composable(
                                route = AppRoute.Cart.name,
                                enterTransition = {
                                    fadeIn(animationSpec = tween(durationMillis = 150))
                                },
                                exitTransition = {
                                    fadeOut(animationSpec = tween(durationMillis = 150))
                                }
                            ) {
                                Text(text = "Cart")
                            }
                            composable(
                                route = AppRoute.Profile.name,
                                enterTransition = {
                                    fadeIn(animationSpec = tween(durationMillis = 150))
                                },
                                exitTransition = {
                                    fadeOut(animationSpec = tween(durationMillis = 150))
                                }
                            ) {
                                Text(text = "Profile")
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
    currentRoute: String,
    navigateToItem: (AppRoute) -> Unit
) {
    NavigationBar {
        val items = listOf(
            BottomNavItems.Home,
            BottomNavItems.Cart,
            BottomNavItems.Profile
        )

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route.name,
                onClick = {navigateToItem(item.route)},
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null)
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
    val route: AppRoute,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItems(route = AppRoute.Home, "Home", icon = Icons.Filled.Home)
    object Cart : BottomNavItems(route = AppRoute.Cart, "Cart", icon = Icons.Filled.ShoppingCart)
    object Profile : BottomNavItems(route = AppRoute.Profile, "Profile", icon = Icons.Filled.Person)
}